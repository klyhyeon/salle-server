package com.salle.server.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "cloud.aws")
@ConstructorBinding
@Getter
public class AWSProperties {

    private final S3 s3;

    public AWSProperties(S3 s3) {
        this.s3 = s3;
    }

    @Getter
    public static class S3 {

        private final String bucket;
        private final String endpointUrl;

        public S3(String bucket, String endpointUrl) {
            this.bucket = bucket;
            this.endpointUrl = endpointUrl;
        }
    }
}
