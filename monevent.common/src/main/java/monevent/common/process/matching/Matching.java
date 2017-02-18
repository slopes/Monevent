package monevent.common.process.matching;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;

import java.util.List;

/**
 * Created by slopes on 01/02/17.
 */
public class Matching extends Configuration {
    private List<String> fields;
    private IQuery query;
    private long expectedMatch;
    private String type;
    private List<String> commands;

    public Matching() {
        this.fields = Lists.newArrayList();
    }

    public Matching(List<String> fields, long expectedMatch, String type, IQuery query, List<String> commands) {
        this.fields = fields;
        this.expectedMatch = expectedMatch;
        this.type = type;
        this.query = query;
        this.commands = commands;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public long getExpectedMatch() {
        return expectedMatch;
    }

    public void setExpectedMatch(long expectedMatch) {
        this.expectedMatch = expectedMatch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public IQuery getQuery() {
        return query;
    }

    public void setQuery(IQuery query) {
        this.query = query;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The name opf the matching cannot be null or empty.");
        if (getExpectedMatch() <= 0)
            throw new ConfigurationException("Expected match must be strictly positive.");
        if (Strings.isNullOrEmpty(getType()))
            throw new ConfigurationException("The type cannot be null or empty.");
        if (getFields() == null || getFields().size() ==0)
            throw new ConfigurationException("The list of fields cannot be null or empty.");

    }
}
