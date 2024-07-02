package kafka_s3_sink_connector.demo;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

// Configuration class for handling S3 connector settings
public class S3SinkConnectorConfig extends AbstractConfig {
    public static final String S3_BUCKET_NAME = "s3.bucket.name"; // Name of the S3 bucket
    public static final String S3_REGION = "s3.region"; // AWS region where the bucket is located

    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(S3_BUCKET_NAME, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "S3 Bucket Name")
            .define(S3_REGION, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "AWS Region");

    public S3SinkConnectorConfig(Map<String, String> parsedConfig) {
        super(CONFIG_DEF, parsedConfig);
    }
}