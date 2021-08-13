package com.lxj.rabbit.producer.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author Xingjing.Li
 * @since 2021/8/12
 */
public class AsyncBaseQueue {
    private final static Logger log = LoggerFactory.getLogger(AsyncBaseQueue.class);
    private final static int THREAD_SIZE = Runtime.getRuntime().availableProcessors();
    private final static int QUEUE_SIZE = 1000;
    private final static ExecutorService threadPool = new ThreadPoolExecutor(
            THREAD_SIZE, THREAD_SIZE, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("rabbitmq_client_async_sender");
                    return thread;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    log.error("async sender is rejected, runnable {}, executor {}", r, executor);
                }
            }
    );

    public static void submit(Runnable runnable){
        threadPool.submit(runnable);
    }

}
