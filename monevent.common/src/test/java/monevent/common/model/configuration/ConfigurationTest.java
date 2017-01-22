package monevent.common.model.configuration;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by steph on 13/03/2016.
 */
public class ConfigurationTest {

    private final ObjectMapper mapper;

    public ConfigurationTest() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JodaModule());
        this.mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    protected void write(File file, Configuration configuration) {
        try {
            mapper.writeValue(file, configuration);
        } catch (JsonGenerationException error) {
            Assert.fail(error.getMessage());
        } catch (JsonMappingException error) {
            Assert.fail(error.getMessage());
        } catch (JsonParseException error) {
            Assert.fail(error.getMessage());
        } catch (IOException error) {
            Assert.fail(error.getMessage());
        }
    }

    protected Configuration read(File file) {
        try {
            return mapper.readValue(file, Configuration.class);
        } catch (IOException error) {
            Assert.fail(error.getMessage());
        }
        return null;
    }
}