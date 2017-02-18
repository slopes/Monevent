package monevent.common.process.chain;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;

import java.util.List;

/**
 * Created by slopes on 08/02/17.
 */
public class Chaining extends Configuration {
    private boolean isRoot;
    private IQuery query;
    private List<String> subFields;
    private IQuery subNodeQuery;
    private List<String> superFields;
    private IQuery superNodeQuery;
    private IQuery completeQuery;
    private List<String> commands;


    public Chaining() {
        this.subFields = Lists.newArrayList();
        this.superFields = Lists.newArrayList();
        this.commands = Lists.newArrayList();
    }

    public Chaining(String name, IQuery query, boolean isRoot, List<String> subFields,IQuery subNodeQuery,  List<String> superFields,IQuery superNodeQuery, IQuery completeQuery, List<String> commands) {
        super(name);
        this.isRoot = isRoot;
        this.query = query;
        this.subFields = subFields;
        this.subNodeQuery = subNodeQuery;
        this.superFields = superFields;
        this.superNodeQuery = superNodeQuery;
        this.completeQuery = completeQuery;
        this.commands = commands;
    }

    public boolean isRoot() {
        return isRoot;
    }

    //TODO : check that is root is unique in the chaining
    public void setRoot(boolean root) {
        isRoot = root;
    }

    public IQuery getQuery() {
        return query;
    }

    public void setQuery(IQuery query) {
        this.query = query;
    }

    public IQuery getSubNodeQuery() {
        return subNodeQuery;
    }

    public void setSubNodeQuery(IQuery subNodeQuery) {
        this.subNodeQuery = subNodeQuery;
    }

    public IQuery getSuperNodeQuery() {
        return superNodeQuery;
    }

    public void setSuperNodeQuery(IQuery superNodeQuery) {
        this.superNodeQuery = superNodeQuery;
    }

    public IQuery getCompleteQuery() {
        return completeQuery;
    }

    public void setCompleteQuery(IQuery completeQuery) {
        this.completeQuery = completeQuery;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    //TODO : manage different fields name
    public List<String> getSubFields() {
        return subFields;
    }

    public void setSubFields(List<String> subFields) {
        this.subFields = subFields;
    }

    public List<String> getSuperFields() {
        return superFields;
    }

    public void setSuperFields(List<String> superFields) {
        this.superFields = superFields;
    }

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The name opf the chaining cannot be null or empty.");
        if (getSubNodeQuery() == null && getSuperNodeQuery() == null)
            throw new ConfigurationException("At least sub node query or super node query must be defined.");
        if (getCompleteQuery() == null )
            throw new ConfigurationException("The complete query cannot be null.");

    }
}
