package com.salle.server.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "aws")
@ConstructorBinding
@Getter
@RequiredArgsConstructor
public class AWSProperties {

    private final S3 s3;

    @Getter
    @RequiredArgsConstructor
    public static class S3 {

        private final String bucket;

    }
}
