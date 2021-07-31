**Pensioner Detail Service**
  
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
        
    
