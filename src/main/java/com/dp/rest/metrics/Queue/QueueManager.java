package com.dp.rest.metrics.Queue;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.LinkedList;
import java.util.Queue;


public class QueueManager {

    private final Queue queue;

    public QueueManager(MetricRegistry metrics, String name) {

        this.queue = new LinkedList();
        metrics.register(MetricRegistry.name(QueueManager.class, name, "size"),
                new Gauge<Integer>() {
                    @Override
                    public Integer getValue() {
                        return queue.size();
                    }
                });
    }

    public void addJob(Object job){
        this.queue.add(job);
    }


}
