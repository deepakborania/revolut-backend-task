/*
 * This file is generated by jOOQ.
 */
package com.revolut.task.models;


import com.revolut.task.models.tables.Account;
import com.revolut.task.models.tables.Currency;
import com.revolut.task.models.tables.Transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 76227872;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.ACCOUNT</code>.
     */
    public final Account ACCOUNT = com.revolut.task.models.tables.Account.ACCOUNT;

    /**
     * The table <code>PUBLIC.CURRENCY</code>.
     */
    public final Currency CURRENCY = com.revolut.task.models.tables.Currency.CURRENCY;

    /**
     * The table <code>PUBLIC.TRANSACTIONS</code>.
     */
    public final Transactions TRANSACTIONS = com.revolut.task.models.tables.Transactions.TRANSACTIONS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Account.ACCOUNT,
            Currency.CURRENCY,
            Transactions.TRANSACTIONS);
    }
}
