# Revolut Backend Task
This is an implementation for Revolut Money transfer API for the coding challenge.

###Steps to run:
- mvn test : To run the test cases


I have used following frameworks/libraries for this task:
- Java 8
- Spark for web server
- Jooq for connecting to database
- H2 in-memory database
- Rest Assured for ReST endpoint testing
- JUnit 5 for test cases
- Flyway for DB migration

SQL in `V1__createtablesanddata.sql` is run on server startup to create DB structure and prepopulate the data in the in memory database.

###### Due to time constraints most of the test cases are end-to-end test cases which demonstrate the concurrency of the APIs.

In the interest of simplicity we have some assumptions:
- We do not have a person entity to which accounts will be linked. Accounts are considered independent here. 
- I have assumed that any account can transfer to any other account. In the real world we might have some kind of payee registration endpoint. 

Accounts can be in different currency. All the transactions will be converted to the accounts' native currency.
`currency` table hold different currency codes (like GBP, INR, USD) along with their exchange rates in terms of GBP.

`account` table hold the account with its currency and account name.

`transactions` table holds all the transactions which have been done with API. `accountID=0` signifies account withdrawal or deposit. Incase of transfers it has proper from and to account IDs along with currency.
 
Following end points have been implemented:

|Endpoint|Method|Description|Sample
|---|---|---|---|
|/api/accounts/:id|GET|Get account with ID|`curl localhost:8080/api/accounts/10004`|
|/api/accounts|POST|Create a new account|`curl -X POST localhost:8080/api/accounts -d '{"name": "Savings Account", "currencyCode": "INR"}'`|
|/api/accounts/:id/deposit/:currency/:amount|PUT|Deposit money in account|`curl -X PUT localhost:8080/api/accounts/10001/deposit/GBP/100`|
|/api/accounts/:id/withdraw/:currency/:amount|PUT|Deposit money in account|`curl -X PUT localhost:8080/api/accounts/10001/withdraw/GBP/100`|
|/api/accounts/:id|DELETE|Mark account inactive|`curl -X DELETE localhost:8080/api/accounts/10006`|
|/api/txn/transfer/:fromID/:toID/:currency/:amount|POST|Transfer money from account *fromID* to *toID* with the given currency|` curl -X POST localhost:8080/api/txn/transfer/10002/10001/GBP/10`|
|/api/txn/:id|GET|Get list of all transactions which involves this account|`curl localhost:8080/api/txn/10001`|
