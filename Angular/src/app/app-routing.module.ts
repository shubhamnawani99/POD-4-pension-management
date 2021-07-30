import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { PensionDetailsComponent } from './components/pension-details/pension-details.component';
import { ProcessPensionComponent } from './components/process-pension/process-pension.component';
import { AuthGuardService } from './utils/auth-check.guard';
import { RestrictAccessGuard } from './utils/restrict-access.guard';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from "@angular/forms";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

const routes: Routes = [
  { path: "", pathMatch: "full", redirectTo: "/login" },
  { path: "login", component: LoginComponent, canActivate: [RestrictAccessGuard] },
  { path: "home", component: HomeComponent, canActivate: [AuthGuardService] },
  { path: "process-pension", component: ProcessPensionComponent, canActivate: [AuthGuardService] },
  { path: "pension-details", component: PensionDetailsComponent, canActivate: [AuthGuardService] },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  exports: [
    RouterModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
  ]
})
export class AppRoutingModule { }
