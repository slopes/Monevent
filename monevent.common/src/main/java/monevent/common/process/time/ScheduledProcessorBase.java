package monevent.common.process.time;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorBase;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created by Stephane on 26/12/2015.
 */
public abstract class ScheduledProcessorBase extends ProcessorBase {

    private final String cronExpression;
    private final String publication;
    private final EntityBusManager entityBusManager;
    private Scheduler scheduler;

    public ScheduledProcessorBase(String name, IQuery query, String cronExpression, String publication, EntityBusManager entityBusManager) {
        super(name, query);
        this.cronExpression = cronExpression;
        this.publication = publication;
        this.entityBusManager = entityBusManager;
    }

    @Override
    protected void doStart() {

        if (cronExpression != null) {
            try {
                JobKey jobKey = JobKey.jobKey("Job", "Group");
                //Create the jod to be executed
                JobDetail job = JobBuilder.newJob(ScheduledProcessorJob.class).withIdentity(jobKey).build();
                job.getJobDataMap().put("processor", this);
                job.getJobDataMap().put("publication", this.publication);
                job.getJobDataMap().put("entityBusManager", this.entityBusManager);

                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity("Trigger", "Group")
                        .startNow()
                        .withSchedule(
                                CronScheduleBuilder.cronSchedule(this.cronExpression))
                        .build();

                //Schedule the job
                this.scheduler = new StdSchedulerFactory().getScheduler();
                this.scheduler.start();
                Date nextExecution = this.scheduler.scheduleJob(job, trigger);
                trace("New execution scheduled for %s", nextExecution.toString());

            } catch (SchedulerException error) {
                error("Cannot start %s scheduler.", error, getName());
            }
        }

    }

    @Override
    protected void doStop() {
        if (cronExpression != null) {

            try {
                if (this.scheduler != null) {
                    this.scheduler.deleteJob(new JobKey(getName(), getName() + "Group"));
                    this.scheduler.shutdown(true);
                }

            } catch (SchedulerException error) {
                error("Cannot stop %s scheduler.", error, getName());
            }
        }
    }
}


