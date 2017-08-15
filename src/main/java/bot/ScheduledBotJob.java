package bot;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Gard on 15.08.2017.
 */
public class ScheduledBotJob implements Job {
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        System.out.println("My First Quartz Scheduler");

    }

}

