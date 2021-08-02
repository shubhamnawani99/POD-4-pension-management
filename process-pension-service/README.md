**Process Pensioner  Service**
  
  **Description**
      
     It takes in the pensioner detail like the name, aadhaar number, pan detail, self or family or both type of pension
     Verifies if the pensioner detail is accurate by getting the data from PensionerDetail Microservice or not. 
     If not, validation message “Invalid pensioner detail provided, please provide valid detail.”.
     If valid, then pension calculation is done and the pension detail is returned to the Web application to be displayed on the UI.
    
   **Steps and Action**
   
        =>  Pension amount calculation detail is as follows
             1. Self pension: 80% of the last salary earned + allowances
             2. Family pension: 50% of the last salary earned + allowances
        => Pre-defined list of banks and service charge as follows
             1. Public banks – INR 500
             2. Private banks – INR 550
        => pre-defined process codes and its message
             1. 10 – Pension disbursement Success
             2. 21 – Pension amount calculated is wrong, Please redo the calculation
   **Endpoint**
   
      url- http://localhost:8082/pensionerInput 
      This endpoint accept the user request and provides the Pensioner details
      
      Valid Input
      
            {
              "name": "Achyuth",
              "dateOfBirth": "1956-09-12",
              "pan": "BHMER12436",
              "salary": 27000.0,
              "allowance": 10000.0,
              "pensionType": "self",
             
            }
            
        Invalid Input
       
          {
            "message": "Aadhaar Number Not Found",
            "timestamp": "2021-07-31T16:10:28.55679",
            "status": "NOT_FOUND"
           }
        
        
    
