# Money Transfer Application - Backend Case Study



## Rest Services 

## 1) Check Account Balance
#### The api takes the authenticatedAcccountNumber as input and fetches details of the account like balance,accountNumber,bankCode etc.

- _**URI**_ - http://localhost:9999/accounts/checkBalance
- _**HTTP Method**_ - POST
- _**Request Body**_ -
```json
{
    "authenticatedAccountNumber": 92501
}

```
## 2) Add Beneficiary 
#### It Adds beneficiary account (where you want to transfer the money) using this api, adds the account in the authenticated account (users account) linked beneficiaries, the added beneficiary would be disabled currently and would have the default transfer limits set

- _**URI**_ - http://localhost:9999/beneficiaries
- _**HTTP Method**_ - POST
- _**Request Body**_ -
```json
{
    "authenticatedAccountNumber": 92501,
    "beneficiaryDetails": {
        "accountNumber": 92502,
        "beneficiaryName": "Monika",
        "bankCode": "HSBC01342345"
    }
}

```

## 3) Verify Beneficiary 
#### Verify added beneficiary using this api, this can be automated using the OTP implementation through email or sms, the beneficiary account gets ready for the actual transfer after successful calling of this api.

- _**URI**_ - http://localhost:9999/beneficiaries/verify
- _**HTTP Method**_ - PUT
- _**Request Body**_ -
```json
{
    "authenticatedAccountNumber": 92501,
    "beneficiaryAccountNumber": 92502
}

```

## 4) Update Beneficiary Transfer Limits
#### Verify added beneficiary using this api, this can be automated using the OTP implementation through email or sms, the beneficiary account gets ready for the actual transfer after successful calling of this api.

- _**URI**_ - http://localhost:9999/beneficiaries/UpdateTransferlimit
- _**HTTP Method**_ - PUT
- _**Request Body**_ -
```json
{
    "authenticatedAccountNumber": 92501,
    "benefeciaryAccountNumber": 92502,
    "transferLimit": {
        "dailyLimit": "1500.00",
        "maxNoOfTransactions": 3
    }
}

```

## 5) Transfer Money
#### The api after successfully validating the accounts and validity of the beneficiary, transfers the amount from the authenticated account to the beneficiary account

- _**URI**_ - http://localhost:9999/accounts/transfer
- _**HTTP Method**_ - POST
- _**Request Body**_ -
```json
{
    "authenticatedAccountNumber": 92501,
    "beneficiaryAccountNumber": 92502,
    "amount": 100,
    "description": "Free Money "
}

```


## Technologies
#### Java 8
#### Spark Java
#### Google Juice
#### Gson

