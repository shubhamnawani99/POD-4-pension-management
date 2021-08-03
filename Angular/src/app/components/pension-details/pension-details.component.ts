import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PensionDetail } from 'src/app/models/pension-detail';
import { PensionerInput } from 'src/app/models/pensioner-input';
import { AuthService } from 'src/app/services/auth.service';
import { ProcessPensionService } from 'src/app/services/process-pension.service';

@Component({
  selector: 'app-pension-details',
  templateUrl: './pension-details.component.html',
  styleUrls: ['./pension-details.component.css']
})

export class PensionDetailsComponent implements OnInit {

  constructor(
    private pservice: ProcessPensionService,
    private authservice: AuthService,
    private router: Router,
  ) { }

  msg: string = ''
  color: String = ''
  fieldErrors: string[] = []
  types = ["Self", "Family"]

  ngOnInit(): void {
  }

  handleReset() {
    this.msg = ""
    this.fieldErrors = []
  }

  pensionDetails = new PensionDetail("", new Date, "", "", 0.0)
  pensionerInput = new PensionerInput("", new Date, "", "", "")

  handlePensionerInput() {
    console.log(this.pensionerInput);

    this.pservice.getPensionDetails(this.pensionerInput)
      .subscribe(
        data => {
          this.color = "text-success"
          this.pensionDetails = data
          this.msg = "The pension amount is " + this.pensionDetails.pensionAmount + "\nPlease make a note of this";
        },
        error => {
          try {
            // get the errors thrown by the server
            this.fieldErrors = error.error.fieldErrors;
            console.log(this.fieldErrors);
            if (this.fieldErrors.length == 1) {
              this.logoutIfTokenExpired(this.fieldErrors[0])
            }
          } catch (e) {
            // feign error if field error can't be parsed ...
            this.msg = "Service is down, please try again later..."
            this.color = "text-danger"
            console.log(this.msg);
          }
        }
      );
  }

  logoutIfTokenExpired(error: String) {
    if (error.includes("expired")) {
      alert("Your session has been expired... Logging out!");
      this.authservice.logout();
    }
  }
}
