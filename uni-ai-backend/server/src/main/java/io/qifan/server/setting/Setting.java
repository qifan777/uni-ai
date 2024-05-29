package io.qifan.server.setting;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Setting {
    private BigDecimal ocrPrice;
    private BigDecimal tokenPrice;
}
