# Money Transfer api

## Overview
This is an API for transferring money between accounts using JAX-RS, Jersey, embedded Tomcat and Maven. A number of assumptions are made, as listed under the API design header.

## Building and executing

Use the provided executable:

```java -jar transfer-money-api-1.0-SNAPSHOT.jar```

Or rebuild the project:

```mvn clean compile```

```mvn package```

```java -jar target/transfer-money-api-1.0-SNAPSHOT.jar``` 

## Endpoints

The embedded server will start on `http://localhost:8080/`

### /transfer/from/{accountFrom}/to/{accountTo}/amount/{amount}
Transfer amount from one account to another. Working example:
```http://localhost:8080/transfer/from/ABC/to/testAccount/amount/5.5```

### /account/{accountId}/balance
View balance for given account (for example testAccount)
```http://localhost:8080/account/testAccount/balance```

## API design / requirements

Based on the explicit *requirements*:
* Used by multiple systems and services => will need to be scalable and thread-safe
* In-memory datastore => data is not persisted
* Standalone executable => embedded server is required
* Tests for proving API works (including thread-safety)

Some initial **assumptions**:
* Account management has its own API and accounts will be assumed to have been created. 
* As the use of a database is not required for this exercise, some dummy accounts will be initialized in-memory.
* The usual functions of a bank account will be in a different API (for actions such as deposit, withdraw)
* By using Tomcat I get multi-threading out of the box so I will only need to take care of thread-safety

### **NOT** in scope of this API:
* Account management (creation, closure etc.)
* Depositing, withdrawing

### **NOT** in scope due to time limitations:
* Currencies
* Negative balances (overdraft)
* Transfer history
* Recurring transfers
* Scheduled transfers
* Cancelling transfers
* Reversing transfers
* Money requests
* Customisable reference messages (for transfers)
* Metrics, logging, alarms
* Proper error handling (with explicit exception objects)
* Fraud detection

