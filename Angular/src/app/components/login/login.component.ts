import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  msg: string = ''
  color: String = ''
  return: string = '';
  fieldErrors: string[] = []

  constructor(
    private authService: AuthService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  userModel = new User("", "")

  handleReset() {
    this.msg = ""
    this.fieldErrors = []
  }

  // handle the login process
  handleLogin() {    
    // process-login
    this.authService.login(this.userModel)
      .subscribe(
        // if login was successful
        data => {
          this.authService.setSession(data);
          this.router.navigateByUrl("home");
        },
        // if login failed, display the error
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
