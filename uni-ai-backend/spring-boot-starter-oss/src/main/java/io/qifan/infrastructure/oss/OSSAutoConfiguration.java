package io.qifan.infrastructure.oss;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSProperties;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSService;
import io.qifan.infrastructure.oss.local.LocalOSSProperties;
import io.qifan.infrastructure.oss.local.LocalOSSService;
import io.qifan.infrastructure.oss.service.OSSService;
import io.qifan.infrastructure.oss.tencent.TencentOSSProperties;
import io.qifan.infrastructure.oss.tencent.TencentOSSService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(value = {OSSProperties.class, TencentOSSProperties.class, AliYunOSSProperties.class, LocalOSSProperties.class})
public class OSSAutoConfiguration {

    @AutoConfiguration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "LOCAL")
    @EnableConfigurationProperties(LocalOSSProperties.class)
    public static class LocalConfig {

        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService localOSSService(LocalOSSProperties localOSSProperties) {
            return new LocalOSSService(localOSSProperties);
        }
    }

    @AutoConfiguration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "ALI_YUN")
    @EnableConfigurationProperties(AliYunOSSProperties.class)
    public static class AliYunConfig {


        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService aliYunOSSService(AliYunOSSProperties aliYunOSSProperties) {
            return new AliYunOSSService(aliYunOSSProperties);
        }
    }

    @AutoConfiguration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "TENCENT")
    @EnableConfigurationProperties(TencentOSSProperties.class)
    public static class TencentConfig {


        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService tencentOSSService(TencentOSSProperties properties) {
            return new TencentOSSService(properties);
        }
    }

}
