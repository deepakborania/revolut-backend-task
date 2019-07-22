package com.revolut.task.db;

import com.google.inject.Singleton;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Provides the {@link DSLContext} to repositories for performing database functions.
 */
@Singleton
public class DBManagerImpl implements DBManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(DBManagerImpl.class);

    private DataSource dataSource;
    private DSLContext dslContext;

    /**
     * Initializes the database conncetion and migrates by reading in configuration from resources/db.properties
     */
    public DBManagerImpl() {
        ComboPooledDataSource cDataSource;
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            cDataSource = new ComboPooledDataSource();

            cDataSource.setDriverClass(properties.getProperty("db.driver"));
            cDataSource.setJdbcUrl(properties.getProperty("db.url"));
            cDataSource.setUser(properties.getProperty("db.username"));
            cDataSource.setPassword(properties.getProperty("db.password"));
//            cDataSource.setConnectionCustomizerClassName("com.revolut.task.db.IsolationLevelCustomizer");
            this.dataSource = cDataSource;
            this.dslContext = DSL.using(cDataSource, SQLDialect.H2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LOGGER.info("Migrating database...");
        Flyway flyway = new Flyway();
        flyway.setDataSource(this.dataSource);
        flyway.migrate();
        LOGGER.info("Migration complete");
    }


    @Override
    public DSLContext getDSL() {
        return dslContext;
    }
}
