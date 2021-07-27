import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PensionDetail } from './pension-detail';
import { PensionerInput } from './pensioner-input';
import { ProcessPensionResponse } from './process-pension-response';

@Injectable({
  providedIn: 'root'
})
export class ProcessPensionService {

  baseUrl: string = 'http://localhost:8082'

  constructor(private http: HttpClient) { }

  processPension(response: ProcessPensionResponse) {
    return this.http.post(`${this.baseUrl}/processPension`, response);
  }

  getPensionDetails(pensionerInput: PensionerInput): Observable<PensionDetail> {
    return this.http.post<PensionDetail>(`${this.baseUrl}/pensionerInput`, pensionerInput);
  }

}
