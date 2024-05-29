package io.qifan.infrastructure.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSProperties;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSService;
import io.qifan.infrastructure.oss.controller.OSSController;
import io.qifan.infrastructure.oss.local.LocalOSSProperties;
import io.qifan.infrastructure.oss.local.LocalOSSService;
import io.qifan.infrastructure.oss.service.OSSService;
import io.qifan.infrastructure.oss.tenant.TenantOSSProperties;
import io.qifan.infrastructure.oss.tenant.TenantOSSService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
@EnableConfigurationProperties(OSSProperties.class)
public class OSSAutoConfiguration {

    @Bean
    @ConditionalOnBean(OSSService.class)
    public OSSController ossController(OSSService ossService) {
        return new OSSController(ossService);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "LOCAL")
    @EnableConfigurationProperties(LocalOSSProperties.class)
    public static class LocalConfig {

        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService localOSSService(LocalOSSProperties localOSSProperties) {
            return new LocalOSSService(localOSSProperties);
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "ALI_YUN")
    @EnableConfigurationProperties(AliYunOSSProperties.class)
    public static class AliYunConfig {

        @Bean
        public OSS aliYunOSS(AliYunOSSProperties aliYunOSSProperties) {
            return new OSSClientBuilder().build(aliYunOSSProperties.getEndpoint(),
                    aliYunOSSProperties.getAccessKeyId(),
                    aliYunOSSProperties.getAccessKeySecret());
        }

        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService aliYunOSSService(OSS aliyunOss, AliYunOSSProperties aliYunOSSProperties) {
            return new AliYunOSSService(aliYunOSSProperties, aliyunOss);
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "TENANT")
    @EnableConfigurationProperties(TenantOSSProperties.class)
    public static class TenantConfig {

        @Bean
        public COSClient tenantOSS(TenantOSSProperties properties) {
            COSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
            Region region = new Region(properties.getRegion());
            ClientConfig clientConfig = new ClientConfig(region);
            return new COSClient(cred, clientConfig);
        }

        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService tenantOSSService(COSClient client, TenantOSSProperties properties) {
            return new TenantOSSService(client, properties);
        }
    }

}
