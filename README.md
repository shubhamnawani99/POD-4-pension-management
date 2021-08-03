# POD-4-pension-management
Code for MFPE for the project, Pension Management by POD-4 of cohort INTCDE21IJ028

## Team Members
1. Shubham Nawani (POD Leader)
2. Anas Zubair
3. Sreekanth G
4. Amrithya Balaji

## The application has the following services:

1. Eureka Server
2. Authorization Service
3. Pensioner Detail Service
4. Pension Disbursement Service
5. Process Process Service
6. **Pension Management Portal** (Front-end in Angular)

## 1. Eureka Server
The Eureka Server is responsible for registering all the microservices together so that they can communicate with each other with the help of their application names instead of their IP address which may be dynamic in nature.

## 2. Authorization Service
This service is responsible to provide login access to the application and provide security to it with the help of stateless authentication using JWT Tokens.

### This service provides two controller END-POINTS:

1. Open your spring boot application and run the service.
2. Open your browser and head to this URL - http://localhost:8081/swagger-ui.html#/ this will redirect you to Swagger UI where you can test the service.
3. Select the authorization controller header
4. **Login functionality**
* Select **login** POST method and click try it out
* Then enter these **correct** username and password credentials as follows:

```
{
  "username": "admin1",
  "password": "adminpass@1234"
}
```

* Then hit execute and you will see a JWT Token generated. Copy this token to be used in the next step.
* For these **incorrect** credentials:  

 ```
{
  "username": "admin123",
  "password": "wrongpassword"
}
 ``` 
 
**Response**

```
{
  "message": "Incorrect Username or Password",
  "timestamp": "2021-08-03T11:05:11.8077352",
  "fieldErrors": [
    "Incorrect Username or Password"
  ]
}
```

5. **Validation functionality**
* Select **validation** POST method and click try it out
* Then enter previously generated **valid** Token that you had copied into the Authorization header.
* Then hit execute and you would see `true` in the response body.
* If the token in **invalid** the application throws an appropriate error response related to either `Token expired`, `Token malformed` or `Token signature incorrect`.

## 3. Pensioner Detail Service
  
  **Description**
      
      This microservice is responsible for Provides information about the registered pensioner detail i.e., 
      Pensioner name, PAN, bank name, bank account number, bank type – private or public
    
   **Steps and Action**
   
      => This Microservice is to fetch the pensioner detail by the Aadhaar number.
      => Flat file(CSV file with pre-defined data) should be created as part of the Microservice. 
      => This file has to contain data for 20 Pensioners. This has to be read and loaded into List for 
         ALL the operations of the microservice.
      
   **Endpoint**
   
      url- http://localhost:8083/pensionerDetailByAadhaar/123456789012 
      This endpoint accept the user request and provides the Pensioner details. Access this using the POSTMAN client
      
      Input - Aadhaar Number => 123456789012
      
**Valid Response**
      
```
{
  "name": "Achyuth",
  "dateOfBirth": "1956-09-12",
  "pan": "BHMER12436",
  "salary": 27000,
  "allowance": 10000,
  "pensionType": "self",
  "bank": {
    "bankName": "ICICI",
    "accountNumber": 12345678,
    "bankType": "private"
  }
}
```
**Invalid Response**
       
```
{
  "message": "Aadhaar Number Not Found",
  "timestamp": "2021-08-03T11:00:23.7960535",
  "fieldErrors": [
    "Aadhaar Number Not Found"
  ]
}
```
        
## 4. Pension Disbursement Servicee
  
<h3>Description:</h3>
<p>This microservice is responsible for verifying pension amount and bank charges for provided aadhaar number. Single endpoint is present on the controller
to process this request</p>

<h3>Endpoint:</h3>
<b>URL</b> -  <a href="http://localhost:8084/DisbursePension">http://localhost:8084/DisbursePension</a>
This endpoint only accept authenticated request so make sure that there is is valid token present in "Authentication" header. Use AUTH-SERVICE to generate tokens

<h4>Valid Input</h4>

```
{
    "aadhaarNumber":"123456789012",
    "pensionAmount":"31600",
    "bankServiceCharge":"550"
}
```
<h4>Invalid Input</h4>

```
{
    "aadhaarNumber":"123456789012",
    "pensionAmount":"31000",
    "bankServiceCharge":"550"
}
```
<br/>
<p>If details are valid, pension amount and bankCharges is correct then user will following response with code "10" else "21"</p>

<h4>Response:</h4>

```
{
    "processPensionStatusCode": 10
}
```
<p>If request format is invalid then following error response is sent back</p>

```
{
    "message": "Invalid Request"
    "timestamp": "2021-07-30T19:45:18.3272518"
    "fieldErrors": [
        "Aadhaar number can not be left blank",
        "pensionAmount is invalid"
    ]
}
```
<p>If aadhaar number does not exist or there is some internal server error then it will send an following error resonse</p>

```
{
    "message": "Invalid Request",
    "timestamp": "2021-07-30T19:45:18.3272518"
}
```
     
## 5. Process Pension Service
* It takes in the pensioner detail like the name, aadhaar number, pan detail, self or family or both type of pension
* Verifies if the pensioner detail is accurate by getting the data from PensionerDetail Microservice or not. 
* If not, validation message `“Invalid pensioner detail provided, please provide valid detail.”`
* If valid, then pension calculation is done and the pension detail is returned to the Web application to be displayed on the UI.

### This service provides two controller end-points:

1. Open your spring boot application and run the service.
2. Open your browser and head to this URL - http://localhost:8082/swagger-ui.html#/ this will redirect you to Swagger UI where you can test the service.
3. This endpoint only accept authenticated request so make sure that there is is valid token present in "Authentication" header. Use AUTH-SERVICE to generate tokens

4. **Get Pension Details functionality**
Select **/pensionerInput** POST method and click try it out
**Valid Input**

```
{
  "aadhaarNumber": "123456789012",
  "dateOfBirth": "1956-09-12",
  "name": "Achyuth",
  "pan": "BHMER12436",
  "pensionType": "self"
}
```

**Response for valid input**

```
{
  "name": "Achyuth",
  "dateOfBirth": "12/09/1956",
  "pan": "BHMER12436",
  "pensionType": "self",
  "pensionAmount": 31600
}
```

**Invalid Input**

```
{
  "aadhaarNumber": "123456789012",
  "dateOfBirth": "1956-09-12",
  "name": "Achyuth",
  "pan": "BHMER12436",
  "pensionType": "family"
}
```

**Response for invalid input**

```
{
  "message": "Details entered are incorrect",
  "timestamp": "2021-08-03T10:50:58.1047198",
  "fieldErrors": [
    "Details entered are incorrect"
  ]
}
```

**Invalid Input - wrong Aadhaar number**

```
{
  "aadhaarNumber": "223456789012",
  "dateOfBirth": "1956-09-12",
  "name": "Achyuth",
  "pan": "BHMER12436",
  "pensionType": "family"
}
```

**Response for invalid input**

```
{
  "message": "Aadhaar Number Not Found",
  "timestamp": "2021-08-03T10:51:36.344356",
  "fieldErrors": [
    "Aadhaar Number Not Found"
  ]
}
```

**Response for expired token**

```
{
  "message": "Token has been expired",
  "timestamp": "2021-08-03T10:54:10.5174319",
  "fieldErrors": [
    "Token has been expired"
  ]
}
```

5. **Process Pension functionality**
* Select **/processPension** POST method and click try it out
* Status code of 10 for valid input and if the input has been processed by the disbursement microservice
* Status code of 21 for invalid input where the service tries to send a request 2 more times to the disbursement service.


**Valid Input**

```
{
  "aadhaarNumber": "123456789012",
  "bankServiceCharge": 550,
  "pensionAmount": 31600
}
```

**Response**

```
{
  "processPensionStatusCode": 10
}
```

**Invalid Input**

```
{
  "aadhaarNumber": "123456789012",
  "bankServiceCharge": 550,
  "pensionAmount": 991600
}
```

**Response**

```
{
  "processPensionStatusCode": 21
}
```

**Invalid Input - wrong aadhaar number**

```
{
  "aadhaarNumber": "223456789012",
  "bankServiceCharge": 550,
  "pensionAmount": 31600
}
```

**Response**

```
{
  "message": "Aadhaar Number Not Found",
  "timestamp": "2021-08-03T10:58:31.3557242"
}
```

## 6. Pension Management Portal

This is the front end made using Angular, open VS code and enter `ng serve` in the terminal to run the application at `http:\\localhost:4200`
