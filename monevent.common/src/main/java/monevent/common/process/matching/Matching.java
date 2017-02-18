package monevent.common.process.matching;

import com.google.common.collect.Lists;
import monevent.common.model.query.IQuery;

import java.util.List;

/**
 * Created by slopes on 01/02/17.
 */
public class Matching {
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
}
