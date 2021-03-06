
package co.omise.graylog.plugins.google;

import javax.inject.Inject;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;

import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

public class CloudPubSubInput extends MessageInput {
    private static final String NAME = "Google Cloud Pub/Sub Pull";

    @Inject
    public CloudPubSubInput(@Assisted Configuration configuration, MetricRegistry metricRegistry,
            CloudPubSubPullTransport.Factory transport, LocalMetricRegistry localRegistry, CloudPubSubCodec.Factory codec,
            Config config, Descriptor descriptor, ServerStatus serverStatus) {
        super(metricRegistry, configuration, transport.create(configuration), localRegistry,
                codec.create(configuration), config, descriptor, serverStatus);
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<CloudPubSubInput> {
        @Override
        CloudPubSubInput create(Configuration configuration);

        @Override
        Config getConfig();

        @Override
        Descriptor getDescriptor();
    }

    public static class Descriptor extends MessageInput.Descriptor {
        public Descriptor() {
            super(NAME, false, "");
        }
    }

    @ConfigClass
    public static class Config extends MessageInput.Config {
        @Inject
        public Config(CloudPubSubPullTransport.Factory transport, CloudPubSubCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }
}