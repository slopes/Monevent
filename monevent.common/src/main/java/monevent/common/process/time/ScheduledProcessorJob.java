package monevent.common.process.time;

import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBus;
import monevent.common.managers.ManageableBase;
import monevent.common.model.IEntity;
import monevent.common.model.time.JobExecution;
import monevent.common.process.IProcessor;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by steph on 13/03/2016.
 */
public class ScheduledProcessorJob implements Job {

    private final Logger logger;

    public ScheduledProcessorJob() {
        this.logger = LoggerFactory.getLogger("scheduledProcessorJob");
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        IProcessor processor  = (IProcessor) context.getJobDetail().getJobDataMap().get("processor");
        EntityBusManager entityBusManager  = (EntityBusManager) context.getJobDetail().getJobDataMap().get("entityBusManager");
        String publication  = (String) context.getJobDetail().getJobDataMap().get("publication");

        if ( processor != null) {
            JobExecution jobExecution = new JobExecution(processor.getName());
            jobExecution.setFireTime(new DateTime(context.getFireTime()));
            jobExecution.setFireId(context.getFireInstanceId());
            try {
                IEntity result = processor.process(jobExecution);
                if ( publication != null && entityBusManager != null && result != null) {
                    IEntityBus entityBus = entityBusManager.load(publication);
                    if (entityBus != null) {
                        entityBus.publish(result);
                    }
                }
            } catch (Exception error) {
                ManageableBase.error(logger,"Cannot trigger job %s", error, processor.getName());
            } finally {
                ManageableBase.info(logger,"Job %s done",processor.getName());
            }
        }
    }
}
