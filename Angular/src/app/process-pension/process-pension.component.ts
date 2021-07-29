import { Component, OnInit } from '@angular/core';
import { ProcessPensionInput } from '../process-pension-input';
import { ProcessPensionResponse } from '../process-pension-response';
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

  processPensionResponse = new ProcessPensionResponse(21);
  processPensionInput = new ProcessPensionInput("", 0, 0);

  handleProcessPensionInput() {
    this.pservice.processPension(this.processPensionInput)
      .subscribe(
        data => {
          this.processPensionResponse = data
          if (this.processPensionResponse.processPensionStatusCode == 10) {
            this.msg = "Pension disbursement Success"
            this.color = "text-info"
          }
          else {
            this.msg = "Pension amount calculated is wrong, Please redo the calculation."
            this.color = "text-danger"
          }
          console.log(this.processPensionResponse);
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
