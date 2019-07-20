package com.revolut.task.db;

import com.mchange.v2.c3p0.AbstractConnectionCustomizer;

import java.sql.Connection;

public class IsolationLevelCustomizer extends AbstractConnectionCustomizer {
    @Override
    public void onAcquire(Connection c, String parentDataSourceIdentityToken) throws Exception {
        super.onAcquire(c, parentDataSourceIdentityToken);
        c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    }
}
