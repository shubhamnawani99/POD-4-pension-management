import { Component, OnInit } from '@angular/core';
import { PensionDetail } from '../pension-detail';
import { PensionerInput } from '../pensioner-input';
import { ProcessPensionService } from '../process-pension.service';

@Component({
  selector: 'app-process-pension',
  templateUrl: './process-pension.component.html',
  styleUrls: ['./process-pension.component.css']
})
export class ProcessPensionComponent implements OnInit {

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
          this.msg = "Details are correct"
          this.color = "text-info"
          this.pensionDetails = data
          console.log(this.pensionDetails);
        },
        error => {
          this.fieldErrors = error.error.fieldErrors;
        }
      );
  }
}
