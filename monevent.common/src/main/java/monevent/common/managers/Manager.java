package monevent.common.managers;

import java.util.concurrent.ExecutionException;

/**
 * Created by slopes on 04/03/17.
 */
public class Manager extends ManagerBase<IManageable> {

    private ManageableFactory factory;

    public Manager(ManageableFactory factory) {
        super("manager");
        this.factory = factory;
    }

    @Override
    protected IManageable build(String key) {
         return  factory.build(key,this);
    }



}
