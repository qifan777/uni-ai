package io.qifan.infrastructure.oss.local;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.infrastructure.oss.service.OSSService;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
@Slf4j
public class LocalOSSService implements OSSService {

  private final LocalOSSProperties localOSSProperties;

  @SneakyThrows
  @Override
  public String upload(MultipartFile multipartFile) {
    return basicUpload(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
  }

  @Override
  public String upload(String objectName, InputStream inputStream) {
    return basicUpload(objectName, inputStream);
  }

  @SneakyThrows
  public String basicUpload(String objectName, InputStream inputStream) {
    byte[] bytes = inputStream.readAllBytes();
    String fileName = UUID.randomUUID() + "_" + objectName;
    try (FileOutputStream fileOutputStream = new FileOutputStream(
        localOSSProperties.getPath() + "/" + fileName)
    ) {
      fileOutputStream.write(bytes);
    } catch (Exception e) {
      log.error("上传失败", e);
      throw new BusinessException(ResultCode.Fail, "上传失败");
    }
    return localOSSProperties.getUrl() + fileName;
  }

}
