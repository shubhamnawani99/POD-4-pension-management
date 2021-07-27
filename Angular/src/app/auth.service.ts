import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  baseUrl: string = 'http://localhost:8081'
  private isUserLoggedIn: boolean = false;
  private isUserValid: boolean = false;

  login(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, user, { responseType: 'text' })
  }

  // utility to check if the user is still logged in 
  isLoggedIn() {
    return this.isUserLoggedIn;
  }

  setSession(token: string) {
    this.isUserLoggedIn = true;
    localStorage.setItem('token', token);
  }

  getToken() {
    return localStorage.getItem("token");
  }


  isValid() {
    const token = this.getToken();
    if (token == undefined || token == '' || token == null) {
      return false;
    }
    this.setValidity();
    return this.isUserValid;

  }

  setValidity(): Observable<boolean> {
    const result = new Subject<boolean>();

    this.valdiateAdmin().subscribe(data => {
      result.next(true);
      this.isUserValid = true
      result.complete();
    },
      error => {
        result.next(false);
        this.isUserValid = false
        result.complete();
      });

    return result.asObservable();
  }

  // calls the validate method in the backend to see the token validity
  valdiateAdmin() {
    return this.http.get<boolean>(`${this.baseUrl}/validate`);
  }

  logout() {
    localStorage.removeItem('token');
  }

}
