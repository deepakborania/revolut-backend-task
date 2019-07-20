/*
 * This file is generated by jOOQ.
 */
package com.revolut.task.models;


import com.revolut.task.models.tables.Account;
import com.revolut.task.models.tables.Currency;
import com.revolut.task.models.tables.Transactions;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index PRIMARY_KEY_E = Indexes0.PRIMARY_KEY_E;
    public static final Index CONSTRAINT_INDEX_5 = Indexes0.CONSTRAINT_INDEX_5;
    public static final Index PRIMARY_KEY_5 = Indexes0.PRIMARY_KEY_5;
    public static final Index PRIMARY_KEY_F = Indexes0.PRIMARY_KEY_F;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index PRIMARY_KEY_E = Internal.createIndex("PRIMARY_KEY_E", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.ID }, true);
        public static Index CONSTRAINT_INDEX_5 = Internal.createIndex("CONSTRAINT_INDEX_5", Currency.CURRENCY, new OrderField[] { Currency.CURRENCY.CODE }, true);
        public static Index PRIMARY_KEY_5 = Internal.createIndex("PRIMARY_KEY_5", Currency.CURRENCY, new OrderField[] { Currency.CURRENCY.ID }, true);
        public static Index PRIMARY_KEY_F = Internal.createIndex("PRIMARY_KEY_F", Transactions.TRANSACTIONS, new OrderField[] { Transactions.TRANSACTIONS.ID }, true);
    }
}
