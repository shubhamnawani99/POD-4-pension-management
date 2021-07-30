import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'pension-management';
  
  constructor(private router: Router,
    private authService: AuthService) { }

  isLoggedIn() {
    return this.authService.isLoggedIn()
  }

  logout() {
    this.authService.logout();
  }
}
