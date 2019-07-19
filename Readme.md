#Money Transfer api

## Overview
This is an API for transferring money between accounts. Therefore a number of assumptions are made, as listed under the API design header

## Building and executing
```mvn clean compile```

```mvn package```

```java -jar target/transfer-money-api-1.0-SNAPSHOT.jar``` 

## Endpoints

The embedded server will start on `http://localhost:8080/`

This api has functions in the **/transfer** path

### /charge

### /send

## API design / requirements

Based on the explicit requirements:
* Used by multiple systems and services => will need to be scalable and thread-safe
* In-memory datastore => data is not persisted
* Standalone executable => embedded server is required
* Tests for proving API works

Some initial assumptions:
* Account management has its own API and accounts will be assumed to have been created. 
* As the use of a database is not required for this exercise, some dummy accounts will be initialized in-memory.
* The usual functions of a bank account will be in a different API (for actions such as deposit, withdraw)
* Writing tests for thread-safety is hard and will not be in scope
* By using Tomcat I get multi-threading out of the box so I will only need to take care of thread-safety

**NOT** in scope of this API:
* Account management (creation, closure etc.)
* Depositing, withdrawing

**NOT** in scope due to time limitations:
* Transfer history
* Recurring transfers
* Scheduled transfers
* Cancelling transfers
* Reversing transfers
* Money requests
* Customisable reference messages (for transfers)
* Metrics, logging, alarms
* Fraud detection

