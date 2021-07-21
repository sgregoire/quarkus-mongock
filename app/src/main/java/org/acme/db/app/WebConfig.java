package org.acme.db.app;

import com.github.cloudyrock.mongock.driver.mongodb.v3.driver.MongoCore3Driver;
import com.github.cloudyrock.standalone.MongockStandalone;
import com.mongodb.client.MongoClient;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.acme.db.mgmt.Changelog;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class WebConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @ConfigProperty(name="quarkus.mongodb.database")
    String dbName;

    @Inject
    MongoClient mongoClient;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        MongoCore3Driver driver = MongoCore3Driver.withDefaultLock(mongoClient, dbName);

        MongockStandalone.Runner runner = MongockStandalone.builder()
            .setDriver(driver)
            .addChangeLogsScanPackage(Changelog.class.getPackage().getName())
            .buildRunner();
        runner.execute();
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping... {}");
    }
}
