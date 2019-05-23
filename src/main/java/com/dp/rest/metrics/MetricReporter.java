package com.dp.rest.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

public class MetricReporter {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void startReport() {
        Meter requests = metrics.meter("requests");
        requests.mark();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

        wait5Seconds();
    }

    private static void wait5Seconds() {
        try {
            Thread.sleep(5*1000);
        }
        catch(InterruptedException e) {}
    }
}
