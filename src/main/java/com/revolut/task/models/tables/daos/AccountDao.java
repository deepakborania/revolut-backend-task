/*
 * This file is generated by jOOQ.
 */
package com.revolut.task.models.tables.daos;


import com.revolut.task.models.tables.Account;
import com.revolut.task.models.tables.records.AccountRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class AccountDao extends DAOImpl<AccountRecord, com.revolut.task.models.tables.pojos.Account, Integer> {

    /**
     * Create a new AccountDao without any configuration
     */
    public AccountDao() {
        super(Account.ACCOUNT, com.revolut.task.models.tables.pojos.Account.class);
    }

    /**
     * Create a new AccountDao with an attached configuration
     */
    public AccountDao(Configuration configuration) {
        super(Account.ACCOUNT, com.revolut.task.models.tables.pojos.Account.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.revolut.task.models.tables.pojos.Account object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>ID IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchById(Integer... values) {
        return fetch(Account.ACCOUNT.ID, values);
    }

    /**
     * Fetch a unique record that has <code>ID = value</code>
     */
    public com.revolut.task.models.tables.pojos.Account fetchOneById(Integer value) {
        return fetchOne(Account.ACCOUNT.ID, value);
    }

    /**
     * Fetch records that have <code>NAME IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchByName(String... values) {
        return fetch(Account.ACCOUNT.NAME, values);
    }

    /**
     * Fetch records that have <code>BALANCE IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchByBalance(BigDecimal... values) {
        return fetch(Account.ACCOUNT.BALANCE, values);
    }

    /**
     * Fetch records that have <code>ACTIVE IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchByActive(Boolean... values) {
        return fetch(Account.ACCOUNT.ACTIVE, values);
    }

    /**
     * Fetch records that have <code>CURRENCY_CODE IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchByCurrencyCode(String... values) {
        return fetch(Account.ACCOUNT.CURRENCY_CODE, values);
    }

    /**
     * Fetch records that have <code>CREATED_ON IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchByCreatedOn(LocalDateTime... values) {
        return fetch(Account.ACCOUNT.CREATED_ON, values);
    }

    /**
     * Fetch records that have <code>UPDATED_ON IN (values)</code>
     */
    public List<com.revolut.task.models.tables.pojos.Account> fetchByUpdatedOn(LocalDateTime... values) {
        return fetch(Account.ACCOUNT.UPDATED_ON, values);
    }
}
