package monevent.common.model.configuration.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by steph on 28/02/2016.
 */
public class FileConfigurationFactoryTest {

    @Test
    public void testReadWrite() throws Exception {

        Query queryWrite = new Query("writeQuery");
        queryWrite.addCriterion("name", "EventA", QueryCriterionType.Is);
        queryWrite.addCriterion("type", "event", QueryCriterionType.Is);
        queryWrite.addCriterion("@timestamp", DateTime.now(), QueryCriterionType.GreaterThan);
        queryWrite.addCriterion("value", 12.0, QueryCriterionType.LesserThan);
        File file = new File("src/test/resources/config/queries/query.json");
        try {

            /*** write to file ***/
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.writeValue(file, queryWrite); // {

            /*** read from file ***/
            Query queryRead = mapper.readValue(file, Query.class);
            Assert.assertEquals(queryWrite.getCriteria().get(0).getValue(), queryRead.getCriteria().get(0).getValue());

        } finally {
            file.delete();
        }
    }


}