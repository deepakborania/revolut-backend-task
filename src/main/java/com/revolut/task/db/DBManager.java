package com.revolut.task.db;

import org.jooq.DSLContext;

public interface DBManager {
    DSLContext getDSL();
}
