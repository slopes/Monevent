package monevent.common.managers.configuration;

import monevent.common.communication.EntityBusConfiguration;
import monevent.common.managers.ManageableFactory;
import monevent.common.model.configuration.factory.IConfigurationFactory;
import monevent.common.model.configuration.factory.file.FileConfigurationFactory;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by slopes on 26/02/17.
 */
public class ConfigurationManagerTest {


    @Test
    public void testGetAndSetFactories() throws Exception {
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.start();
        configurationManager.setFactory(new FileConfigurationFactory<EntityBusConfiguration>("entityBusFactory1", "src/test/resources/configuration.manager/test1"));
        configurationManager.setFactory(new FileConfigurationFactory<EntityBusConfiguration>("entityBusFactory2", "src/test/resources/configuration.manager/test2"));
        IConfigurationFactory<EntityBusConfiguration> factory1 = configurationManager.getFactory("entityBusFactory1");
        assertNotNull(factory1);
        assertEquals("entityBusFactory1", factory1.getName());
        IConfigurationFactory<EntityBusConfiguration> factory2 = configurationManager.getFactory("entityBusFactory2");
        assertNotNull(factory2);
        assertEquals("entityBusFactory2", factory2.getName());
    }

}