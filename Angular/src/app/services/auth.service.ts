import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  // set your base URL here
  baseUrl: string = 'http:///localhost:8081'

  // hit the backend to login
  // returns error message for invalid login credentials
  login(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, user, { responseType: 'text' })
  }

  // method to check if the user is still logged in
  isLoggedIn() {
    return this.getToken() != null
  }

  // method to get token from local storage
  getToken() {
    return localStorage.getItem("token");
  }

  // method to set to session, by storing token in local storage
  setSession(token: string) {
    localStorage.setItem('token', token);
  }

  // logout if 
  // 1. either the user's token (session) expires
  // 2. or if the user presses the logout button
  // and redirect back to login page
  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl("login");
  }

}
