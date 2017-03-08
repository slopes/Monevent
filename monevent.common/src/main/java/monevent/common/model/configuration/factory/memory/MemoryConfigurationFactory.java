package monevent.common.model.configuration.factory.memory;

import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.factory.ConfigurationFactoryBase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by slopes on 04/03/17.
 */
public class MemoryConfigurationFactory<T extends Configuration> extends ConfigurationFactoryBase<T> {
    private final Map<String, T> configurations;

    public MemoryConfigurationFactory(String name) {
        super(name);
        this.configurations = new ConcurrentHashMap<>();
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

    @Override
    public void add(T configuration) {
        configurations.put(configuration.getName(),configuration);
    }


    @Override
    public boolean canBuild(String name) {
        return configurations.containsKey(name);
    }

    @Override
    public T build(String name) {
        return this.configurations.get(name);
    }



}
