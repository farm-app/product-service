package ru.rtln.productservice.config.initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import ru.rtln.productservice.config.properties.MinioProperties;
import ru.rtln.productservice.service.ObjectStoreService;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MinioInitializer {

    private final ObjectStoreService objectStoreService;
    private final MinioProperties minioProperties;

    @PostConstruct
    public void init() {
        initializeBuckets();
    }

    private void initializeBuckets() {
        for (final var bucket : minioProperties.getBuckets().values()) {
            objectStoreService.createBucket(bucket);
        }
        log.info("Completion of minio buckets initialization");
    }

}