package com.example.itemsapi.migration;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MigrationJobRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MigrationJobRunner.class);

    private final DataSource dataSource;
    private final ApplicationContext context;

    public MigrationJobRunner(DataSource dataSource, ApplicationContext context) {
        this.dataSource = dataSource;
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!args.containsOption("migrate")) {
            return;
        }
        log.info("Starting database migration job via Flyway...");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
        var result = flyway.migrate();
        log.info("Database migrations completed successfully. Applied migrations: {}", result.migrationsExecuted);
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}