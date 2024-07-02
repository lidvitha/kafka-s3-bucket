package kafka_s3_sink_connector.demo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import java.util.Collection;
import java.util.Map;

// Task class that handles writing records from Kafka to S3
public class S3SinkTask extends SinkTask {
    private AmazonS3 s3Client;
    private String bucketName;

    // Initializes resources such as the S3 client
    @Override
    public void start(Map<String, String> props) {
        S3SinkConnectorConfig config = new S3SinkConnectorConfig(props);
        bucketName = config.getString(S3SinkConnectorConfig.S3_BUCKET_NAME);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(config.getString(S3SinkConnectorConfig.S3_REGION))
                .build();
    }

    // Processes each SinkRecord, uploading it to S3
    @Override
    public void put(Collection<SinkRecord> records) {
        for (SinkRecord record : records) {
            s3Client.putObject(new PutObjectRequest(bucketName, generateKey(record), record.value().toString()));
        }
    }

    // Generates a unique key for each object stored in S3
    private String generateKey(SinkRecord record) {
        return "kafka-data/" + System.currentTimeMillis() + "-" + record.kafkaOffset() + ".txt";
    }

    // Cleans up any open resources
    @Override
    public void stop() {
        // Clean up any resources here
    }

    // Returns the version of this task
    @Override
    public String version() {
        return "1.0";
    }
}