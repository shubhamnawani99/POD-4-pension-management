import { Component, OnInit } from '@angular/core';
import { PensionDetail } from '../pension-detail';
import { PensionerInput } from '../pensioner-input';
import { ProcessPensionService } from '../process-pension.service';

@Component({
  selector: 'app-pension-details',
  templateUrl: './pension-details.component.html',
  styleUrls: ['./pension-details.component.css']
})
export class PensionDetailsComponent implements OnInit {

  constructor(private pservice: ProcessPensionService) { }

  msg: string = ''
  color: String = ''
  fieldErrors: string[] = []

  ngOnInit(): void {
  }

  handleReset() {
    this.msg = ""
    this.fieldErrors = []
  }

  pensionDetails = new PensionDetail("", new Date, "", "", 0.0)
  pensionerInput = new PensionerInput("", new Date, "", "", "")

  handlePensionerInput() {
    this.pservice.getPensionDetails(this.pensionerInput)
      .subscribe(
        data => {
          this.color = "text-info"
          this.pensionDetails = data
          this.msg = "The pension amount is " + this.pensionDetails.pensionAmount;
        },
        error => {
          try {
            this.fieldErrors = JSON.parse(error.error).fieldErrors;
          } catch (error) {
            this.msg = "Service is down, please try again later..."
            console.log(this.msg);
          }
        }
      );
  }

}
