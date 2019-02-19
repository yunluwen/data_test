package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * java定时任务接口ScheduledExecutorService:
 * ScheduledExecutorService,是基于线程池设计的定时任务类,
 * 每个调度任务都会分配到线程池中的一个线程去执行,也就是说,任务是并发执行,互不影响。
 * 需要注意,只有当调度任务来的时候,ScheduledExecutorService才会真正启动一个线程,
 * 其余时间ScheduledExecutorService都是出于轮询任务的状态。
 */
public class MyScheduledExecutor implements Runnable{
    //线程任务
    private String jobName;

    MyScheduledExecutor() {

    }

    MyScheduledExecutor(String jobName) {
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println(jobName + " is running");
    }

    public static void main(String[] args) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        long initialDelay = 1;
        long period = 1;
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(new MyScheduledExecutor("job1"),
                initialDelay, period, TimeUnit.SECONDS);

        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
        service.scheduleWithFixedDelay(new MyScheduledExecutor("job2"),
                initialDelay, period, TimeUnit.SECONDS);
    }

}
