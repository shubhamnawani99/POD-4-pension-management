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
	1. Select **login** POST method and click try it out
	2. Then enter these **correct** username and password credentials as follows:
      ```
      { 
        username: "admin1", 
        password: "adminpass@1234"
      }
      ```
	3. Then hit execute and you will see a JWT Token generated. Copy this token to be used in the next step.
	4. For these **incorrect** credentials:     
	  ```
    { 
        username: "admin123", 
        password: "adminpass@1234"
    }
    ``` 
    you would receive a message of `Incorrect username or password`

5. **Validation functionality**
	1. Select **validation** POST method and click try it out
	2. Then enter previously generated **valid** Token that you had copied into the Authorization header.
	3. Then hit execute and you would see `true` in the response body.
	4. If the token in **invalid** the application throws an appropriate error response related to either `Token expired`, `Token malformed` or `Token signature incorrect`.

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
      This endpoint accept the user request and provides the Pensioner details
      
      Valid Input
      
            {
              "name": "Achyuth",
              "dateOfBirth": "1956-09-12",
              "pan": "BHMER12436",
              "salary": 27000.0,
              "allowance": 10000.0,
              "pensionType": "self",
              "bank": {
                "bankName": "ICICI",
                "accountNumber": 12345678,
                "bankType": "private"
              }
            }
            
        Invalid Input
       
          {
            "message": "Aadhaar Number Not Found",
            "timestamp": "2021-07-31T16:10:28.55679",
            "status": "NOT_FOUND"
           }
        
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
