package monevent.common.model.configuration.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.common.collect.ImmutableList;
import monevent.common.communication.EntityBusConfiguration;
import monevent.common.managers.ManageableBase;
import monevent.common.model.IDistinguishable;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.store.StoreConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by steph on 28/02/2016.
 */
public class FileConfigurationFactory extends ManageableBase implements IConfigurationFactory {
    private final String configurationDirectory;
    private final Map<String, StoreConfiguration> stores;
    private final Map<String, ProcessorConfiguration> processors;
    private final Map<String, EntityBusConfiguration> buses;
    private final ObjectMapper mapper;

    public FileConfigurationFactory(String configurationDirectory) {
        super("FileConfiguration");
        this.configurationDirectory = configurationDirectory;
        this.processors = new ConcurrentHashMap<>();
        this.stores = new ConcurrentHashMap<>();
        this.buses = new ConcurrentHashMap<>();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JodaModule());
        this.mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    protected void doStart() {
        info("FileConfigurationFactory started");
        buildCategory("stores", StoreConfiguration.class, stores);
        buildCategory("processors", ProcessorConfiguration.class, processors);
        buildCategory("buses", EntityBusConfiguration.class, buses);
    }

    @Override
    protected void doStop() {
        info("FileConfigurationFactory stopped");
    }

    private <T extends IDistinguishable> void buildCategory(String category, Class<T> valueType, Map<String, T> values) {
        File categoryDirectory = new File(String.format("%s/%s", this.configurationDirectory, category));
        if (categoryDirectory.exists() && categoryDirectory.isDirectory()) {
            File[] files = categoryDirectory.listFiles((d, name) -> name.endsWith(".json"));
            for (File file : files) {
                try {
                    T value = mapper.readValue(file, valueType);
                    values.put(value.getName(), value);
                } catch (IOException jsonError) {
                    error("Cannot deserialize entity.", jsonError);
                }
            }
        }
    }

    @Override
    public StoreConfiguration buildStore(String storeName) {
        return stores.get(storeName);
    }

    @Override
    public EntityBusConfiguration build(String entityBusName) {
        return buses.get(entityBusName);
    }

    @Override
    public ProcessorConfiguration buildProcessor(String processorName) {
        return processors.get(processorName);
    }



}
