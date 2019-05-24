package com.dp.rest.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

import java.util.concurrent.TimeUnit;

public class MetricReporter {

    private MetricRegistry metricsRegistry;

    public MetricReporter(MetricRegistry metricsRegistry) {
        this.metricsRegistry = metricsRegistry;
    }

    public void startReport() {
        Meter requests = metricsRegistry.meter("requests");

        requests.mark();


        // Start console reporter
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricsRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        // Start JMX reporter
        JmxReporter jmxReporter = JmxReporter.forRegistry(metricsRegistry).build();
        jmxReporter.start();

        wait5Seconds();
    }

    private  void wait5Seconds() {
        try {
            Thread.sleep(5*1000);
        }
        catch(InterruptedException e) {}
    }
}
