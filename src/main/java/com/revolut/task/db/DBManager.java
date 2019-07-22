package com.revolut.task.db;

import org.jooq.DSLContext;

/**
 * Provides the {@link DSLContext} to repositories for performing database functions.
 */
public interface DBManager {
    DSLContext getDSL();
}
