package io.qifan.server.oss;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.common.model.R;
import io.qifan.infrastructure.oss.service.OSSService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("oss")
@AllArgsConstructor
public class OSSController {
    private final Map<String, UniOSSService> uniOSSServiceMap;
    private final OSSRepository ossRepository;

    @PostMapping("upload")
    public R<Map<String, String>> upload(@RequestParam Map<String, MultipartFile> files) {
        List<String> arrayList = new ArrayList<>();
        OSSSetting ossSetting = ossRepository.get();
        UniOSSService uniOSSService = uniOSSServiceMap.get(ossSetting.getType().getKeyEnName());
        OSSService ossService = uniOSSService.getOSSService(ossSetting.getOptions());
        files.forEach((String key, MultipartFile file) -> {
            try {
                String url = ossService.upload(file);
                arrayList.add(url);
            } catch (Exception e) {
                throw new BusinessException(ResultCode.TransferStatusError, "上传失败");
            }
        });
        String join = String.join(";", arrayList);
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("url", join);
        return R.ok(urlMap);
    }

    @PostMapping
    public boolean save(@RequestBody OSSSetting ossSetting) {
        ossRepository.save(ossSetting);
        return true;
    }

    @GetMapping
    public OSSSetting get() {
        return ossRepository.get();
    }
}