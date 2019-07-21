package com.revolut.task.repositories;

import com.google.inject.Inject;
import com.revolut.task.db.DBManager;
import com.revolut.task.di.InjectorProvider;
import com.revolut.task.exceptions.AccountNotFoundException;
import com.revolut.task.models.tables.pojos.Account;
import com.revolut.task.models.tables.records.AccountRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;

import static com.revolut.task.models.Tables.ACCOUNT;

public class AccountsRepositoryImpl implements AccountsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsRepositoryImpl.class);

    private final DBManager dbManager;

    @Inject
    public AccountsRepositoryImpl() {
        this.dbManager = InjectorProvider.provide().getInstance(DBManager.class);
    }

    @Override
    public Optional<Account> getAccountByID(int id) {
        final Account[] result = new Account[1];
        try {
            dbManager.getDSL().transaction(configuration -> {
                AccountRecord accRecord = dbManager.getDSL().selectFrom(ACCOUNT)
                        .where(ACCOUNT.ID.eq(id))
                        .fetchOne();

                if (accRecord == null) {
                    throw new AccountNotFoundException(id);
                } else {
                    result[0] = accRecord.into(Account.class);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error in getting account", e);
            return Optional.empty();
        }
        return Optional.of(result[0]);
    }

    @Override
    public Optional<Account> createAccount(String name, String currencyCode) {
        final Account[] result = new Account[1];

        try {
            dbManager.getDSL().transaction(configuration -> {
                result[0] = dbManager.getDSL()
                        .insertInto(ACCOUNT, ACCOUNT.NAME, ACCOUNT.CURRENCY_CODE, ACCOUNT.BALANCE, ACCOUNT.ACTIVE)
                        .values(name, currencyCode.toUpperCase(), BigDecimal.ZERO, true)
                        .returning(ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.CURRENCY_CODE, ACCOUNT.BALANCE)
                        .fetchOne()
                        .into(Account.class);
            });
        } catch (Exception e) {
            LOGGER.error("Error in creating account", e);
            return Optional.empty();
        }
        return Optional.of(result[0]);
    }

    @Override
    public Boolean closeAccount(int accountID) {
        boolean result = false;
        dbManager.getDSL().transaction(configuration -> {
            AccountRecord accRecord = dbManager.getDSL().selectFrom(ACCOUNT)
                    .where(ACCOUNT.ID.eq(accountID))
                    .fetchOne();
            if(accRecord==null){
                throw new AccountNotFoundException(accountID);
            }
            accRecord.setActive(false);
            accRecord.store();
        });
        return true;
    }

}
