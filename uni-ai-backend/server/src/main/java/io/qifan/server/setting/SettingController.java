package io.qifan.server.setting;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("setting")
@AllArgsConstructor
public class SettingController {
    private final SettingRepository settingRepository;

    @GetMapping
    public Setting get() {
        return settingRepository.get();
    }

    @PostMapping
    public void saveSetting(@RequestBody Setting setting) {
        settingRepository.save(setting);
    }
}
