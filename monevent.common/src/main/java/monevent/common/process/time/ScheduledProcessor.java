package monevent.common.process.time;

import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorBase;
import monevent.common.process.ProcessorManager;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by Stephane on 26/12/2015.
 */
public class ScheduledProcessor extends ProcessorBase {

    private final String cronExpression;
    private final List<String> processors;
    private final ProcessorManager processorManager;
    private Scheduler scheduler;

    public ScheduledProcessor(String name, IQuery query, String cronExpression, List<String> processors, ProcessorManager processorManager) {
        super(name, query);
        this.cronExpression = cronExpression;
        this.processors = processors;
        this.processorManager = processorManager;
    }

    @Override
    protected void doStart() {

        if (cronExpression != null) {
            try {
                JobKey jobKey = JobKey.jobKey("Job", "Group");
                //Create the jod to be executed
                JobDetail job = JobBuilder.newJob(ScheduledProcessorJob.class).withIdentity(jobKey).build();
                job.getJobDataMap().put("processor", this);

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

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        if (this.processorManager == null) {
            warn("ProcessorManager is not initialized !");
            return null;
        }

        if (this.processors == null || processors.size() == 0) {
            warn("List of processors is empty or not initialized !");
            return null;
        }

        this.processors.forEach(p -> {
            IProcessor processor = this.processorManager.load(p);
            if (processor != null) {
                processor.process(entity);
            } else {
                warn("Cannot load processor %s", p);
            }
        });
        return null;
    }
}


