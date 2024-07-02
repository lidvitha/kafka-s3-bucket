package kafka_s3_sink_connector.demo;

import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.apache.kafka.common.config.ConfigDef;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class S3SinkConnector extends SinkConnector {
    private Map<String, String> configProps;

    // Returns the version of the connector
    @Override
    public String version() {
        return "1.0";
    }

    // Start method is called when the connector is initialized
    @Override
    public void start(Map<String, String> props) {
        this.configProps = props;
    }

    // Specifies the Task class that handles data transfer to S3
    @Override
    public Class<? extends Task> taskClass() {
        return S3SinkTask.class;
    }

    // Creates configurations for tasks, allowing multiple tasks to be configured
    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> configs = new ArrayList<>();
        for (int i = 0; i < maxTasks; i++) {
            configs.add(new HashMap<>(configProps));
        }
        return configs;
    }

    // Cleanup any resources used by the connector
    @Override
    public void stop() {
    }

    // Defines the configuration setup for the connector
    @Override
    public ConfigDef config() {
        return S3SinkConnectorConfig.CONFIG_DEF;
    }
}
