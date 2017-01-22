package monevent.common.store.memory;

import monevent.common.model.configuration.ConfigurationTest;
import monevent.common.store.StoreConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by steph on 13/03/2016.
 */
public class MemoryStoreTest extends ConfigurationTest {

    @Test
    public void testConfiguration() {
        String name = "memoryStore";

        StoreConfiguration configurationWrite = new MemoryStoreConfiguration(name);
        File file = new File("src/test/resources/config/stores/"+name+".json");
        try {
            write(file, configurationWrite);
            MemoryStoreConfiguration configurationRead = (MemoryStoreConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
        } finally {
            //file.delete();
        }
    }

}