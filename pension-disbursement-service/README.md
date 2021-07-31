<h3>Description:</h3>
<p>This microservice is responsible for verifying pension amount and bank charges for provided aadhaar number. Single endpoint is present on the controller
to process this request</p>

<h3>Endpoint:</h3>
<b>url</b> -  <a href="http://localhost:8084/DisbursePension">http://localhost:8084/DisbursePension</a>
This endpoint only accept authenticated request so make sure that there is is valid token present in "Authentication" header. Use AUTH-SERVICE to generate tokens

<h4>Valid Input</h4>
<code><pre>{
    "aadhaarNumber":"123456789012",
    "pensionAmount":"31600",
    "bankServiceCharge":"550"
}</pre></code>
<h4>Invalid Input</h4>
<code><pre>{
    "aadhaarNumber":"123456789012",
    "pensionAmount":"31000",
    "bankServiceCharge":"550"
}</pre></code>
<br/>
<p>If details are valid, pension amount and bankCharges is correct then user will following response with code "10" else "21"</p>

<h4>Response:</h4>
<code><pre>{
    "processPensionStatusCode": 10
}</pre></code>

<p>If request format is invalid then following error response is sent back</p>
<code><pre>{
    "message": "Invalid Request"
    "timestamp": "2021-07-30T19:45:18.3272518"
    "fieldErrors": [
        "Aadhaar number can not be left blank",
        "pensionAmount is invalid"
    ]
}</pre></code>

<p>If aadhaar number does not exist or there is some internal server error then it will send an following error resonse</p>
<code><pre>{
    "message": "Invalid Request",
    "timestamp": "2021-07-30T19:45:18.3272518"

}</pre></code>
