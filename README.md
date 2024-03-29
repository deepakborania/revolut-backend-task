# Revolut Backend Task
This is an implementation for Revolut Money transfer API for the coding challenge.

### Steps to run:
- `mvn test` : To run the test cases
- `mvn package`: To create a new jar in the target folder (`target/moneytransfer-1.0-SNAPSHOT.jar`)
- `java -jar moneytransfer-1.0-SNAPSHOT.jar`: To startup the server on default 8080 port
- `java -jar target/moneytransfer-1.0-SNAPSHOT.jar -port 9090`: TO start server on a user specified port


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

### Code Description:
- **com.revolut.task.AppServer** is the main class for the application.It starts up the server and setups all different routes handled by the server.
- **com.revolut.api** package contains all the endpoint handlers.
- **com.revolut.task.db** package contains all the database related classes. **com.revolut.task.db.DBManagerImpl** is responsible for connecting to the database and migrating the data from `V1__createtablesanddata.sql`. 
- **com.revolut.task.di** package handles the dependency injection for the application using Guice.
- **com.revolut.task.models** auto-generated by jooQ and contains all the pojos and db mapped objects.
- **com.revolut.task.repositories** contains the repositories used by us for DB operations. Accessed by services.
- **com.revolut.task.service** contains different services which are accessed by handlers to perform their operations. **com.revolut.task.service.LockServiceImpl** contains the Account locking service which allows for locking accounts while in transaction.
- **com.revolut.task.utils** misc utilities
