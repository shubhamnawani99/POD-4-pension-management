import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  // activates for URL where authorization is required
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log("Runninng AUTHENTICAION GUARD..");
    if (!this.authService.isLoggedIn()) {
      this.router.navigateByUrl("login");
    }
    // if the user is valid, only then they can access the resources
    return this.authService.isLoggedIn();
  }

}
