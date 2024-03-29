/*
 * This file is generated by jOOQ.
 */
package com.revolut.task.models.tables;


import com.revolut.task.models.Indexes;
import com.revolut.task.models.Keys;
import com.revolut.task.models.Public;
import com.revolut.task.models.tables.records.TransactionsRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Transactions extends TableImpl<TransactionsRecord> {

    private static final long serialVersionUID = -1439549167;

    /**
     * The reference instance of <code>PUBLIC.TRANSACTIONS</code>
     */
    public static final Transactions TRANSACTIONS = new Transactions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TransactionsRecord> getRecordType() {
        return TransactionsRecord.class;
    }

    /**
     * The column <code>PUBLIC.TRANSACTIONS.ID</code>.
     */
    public final TableField<TransactionsRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>PUBLIC.TRANSACTIONS.FROM_ACCOUNT</code>.
     */
    public final TableField<TransactionsRecord, Integer> FROM_ACCOUNT = createField("FROM_ACCOUNT", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.TRANSACTIONS.TO_ACCOUNT</code>.
     */
    public final TableField<TransactionsRecord, Integer> TO_ACCOUNT = createField("TO_ACCOUNT", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>PUBLIC.TRANSACTIONS.CURRENCY</code>.
     */
    public final TableField<TransactionsRecord, String> CURRENCY = createField("CURRENCY", org.jooq.impl.SQLDataType.VARCHAR(3).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSACTIONS.AMOUNT</code>.
     */
    public final TableField<TransactionsRecord, BigDecimal> AMOUNT = createField("AMOUNT", org.jooq.impl.SQLDataType.DECIMAL.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSACTIONS.CONVERTED_AMOUNT</code>.
     */
    public final TableField<TransactionsRecord, BigDecimal> CONVERTED_AMOUNT = createField("CONVERTED_AMOUNT", org.jooq.impl.SQLDataType.DECIMAL.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.TRANSACTIONS.CREATED_ON</code>.
     */
    public final TableField<TransactionsRecord, LocalDateTime> CREATED_ON = createField("CREATED_ON", org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>PUBLIC.TRANSACTIONS</code> table reference
     */
    public Transactions() {
        this(DSL.name("TRANSACTIONS"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.TRANSACTIONS</code> table reference
     */
    public Transactions(String alias) {
        this(DSL.name(alias), TRANSACTIONS);
    }

    /**
     * Create an aliased <code>PUBLIC.TRANSACTIONS</code> table reference
     */
    public Transactions(Name alias) {
        this(alias, TRANSACTIONS);
    }

    private Transactions(Name alias, Table<TransactionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Transactions(Name alias, Table<TransactionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Transactions(Table<O> child, ForeignKey<O, TransactionsRecord> key) {
        super(child, key, TRANSACTIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_F);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TransactionsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TRANSACTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TransactionsRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_F;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TransactionsRecord>> getKeys() {
        return Arrays.<UniqueKey<TransactionsRecord>>asList(Keys.CONSTRAINT_F);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transactions as(String alias) {
        return new Transactions(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Transactions as(Name alias) {
        return new Transactions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Transactions rename(String name) {
        return new Transactions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Transactions rename(Name name) {
        return new Transactions(name, null);
    }
}
