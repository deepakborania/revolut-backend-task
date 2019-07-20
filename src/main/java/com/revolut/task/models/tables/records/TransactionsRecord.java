/*
 * This file is generated by jOOQ.
 */
package com.revolut.task.models.tables.records;


import com.revolut.task.models.tables.Transactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


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
public class TransactionsRecord extends UpdatableRecordImpl<TransactionsRecord> implements Record7<Integer, Integer, Integer, String, BigDecimal, BigDecimal, LocalDateTime> {

    private static final long serialVersionUID = 739061400;

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.FROM_ACCOUNT</code>.
     */
    public void setFromAccount(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.FROM_ACCOUNT</code>.
     */
    public Integer getFromAccount() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.TO_ACCOUNT</code>.
     */
    public void setToAccount(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.TO_ACCOUNT</code>.
     */
    public Integer getToAccount() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.CURRENCY</code>.
     */
    public void setCurrency(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.CURRENCY</code>.
     */
    public String getCurrency() {
        return (String) get(3);
    }

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.AMOUNT</code>.
     */
    public void setAmount(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.AMOUNT</code>.
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.CONVERTED_AMOUNT</code>.
     */
    public void setConvertedAmount(BigDecimal value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.CONVERTED_AMOUNT</code>.
     */
    public BigDecimal getConvertedAmount() {
        return (BigDecimal) get(5);
    }

    /**
     * Setter for <code>PUBLIC.TRANSACTIONS.CREATED_ON</code>.
     */
    public void setCreatedOn(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.TRANSACTIONS.CREATED_ON</code>.
     */
    public LocalDateTime getCreatedOn() {
        return (LocalDateTime) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, String, BigDecimal, BigDecimal, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, Integer, String, BigDecimal, BigDecimal, LocalDateTime> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Transactions.TRANSACTIONS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Transactions.TRANSACTIONS.FROM_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Transactions.TRANSACTIONS.TO_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Transactions.TRANSACTIONS.CURRENCY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return Transactions.TRANSACTIONS.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field6() {
        return Transactions.TRANSACTIONS.CONVERTED_AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field7() {
        return Transactions.TRANSACTIONS.CREATED_ON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getFromAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getToAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getCurrency();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component5() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal component6() {
        return getConvertedAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component7() {
        return getCreatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getFromAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getToAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getCurrency();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value6() {
        return getConvertedAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value7() {
        return getCreatedOn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value2(Integer value) {
        setFromAccount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value3(Integer value) {
        setToAccount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value4(String value) {
        setCurrency(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value5(BigDecimal value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value6(BigDecimal value) {
        setConvertedAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord value7(LocalDateTime value) {
        setCreatedOn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionsRecord values(Integer value1, Integer value2, Integer value3, String value4, BigDecimal value5, BigDecimal value6, LocalDateTime value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TransactionsRecord
     */
    public TransactionsRecord() {
        super(Transactions.TRANSACTIONS);
    }

    /**
     * Create a detached, initialised TransactionsRecord
     */
    public TransactionsRecord(Integer id, Integer fromAccount, Integer toAccount, String currency, BigDecimal amount, BigDecimal convertedAmount, LocalDateTime createdOn) {
        super(Transactions.TRANSACTIONS);

        set(0, id);
        set(1, fromAccount);
        set(2, toAccount);
        set(3, currency);
        set(4, amount);
        set(5, convertedAmount);
        set(6, createdOn);
    }
}
