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

@Singleton
public class DBManagerImpl implements DBManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(DBManagerImpl.class);

    private DataSource dataSource;
    private final DSLContext dslContext;

    public DBManagerImpl() {
        ComboPooledDataSource cDataSource = new ComboPooledDataSource();
        try {
            cDataSource.setDriverClass("org.h2.Driver");

            cDataSource.setUser("");
            cDataSource.setPassword("");
//            cDataSource.setJdbcUrl("jdbc:h2:file:./my_db");
            cDataSource.setJdbcUrl("jdbc:h2:mem:my_db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dataSource = cDataSource;
        this.dslContext = DSL.using(cDataSource, SQLDialect.H2);

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
