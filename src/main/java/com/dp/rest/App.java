package com.dp.rest;

import com.codahale.metrics.MetricRegistry;
import com.dp.rest.config.Config;
import com.dp.rest.controller.EmployeeController;
import com.dp.rest.metrics.MetricReporter;
import com.dp.rest.metrics.Queue.QueueManager;
import com.dp.rest.service.EmployeeService;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<Config> {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	@Override
	public void initialize(Bootstrap<Config> b) {
		b.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
	}

	@Override
	public void run(Config c, Environment e) throws Exception
	{
		LOGGER.info("Registering REST resources");
		e.jersey().register(new EmployeeController(new EmployeeService(e.getValidator())));

		MetricRegistry metricRegistry = new MetricRegistry();
		MetricReporter metricReporter = new MetricReporter(metricRegistry);
		metricReporter.startReport();

		// Add few jobs to queue
		QueueManager queueManager = new QueueManager(metricRegistry, "name");
		for(int i=0;i<10;i++){
			queueManager.addJob(new Object());
		}
	}

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
}