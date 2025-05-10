package ru.rtln.productservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.rtln.productservice.enums.BucketType;

import java.util.Map;

@Getter
@Setter
@ConfigurationProperties("minio")
public class MinioProperties {

    private String url;
    private String accessKey;
    private String secretKey;
    private Map<String, String> buckets;

    public String getBucket(BucketType key) {
        return buckets.get(key.getName());
    }
}