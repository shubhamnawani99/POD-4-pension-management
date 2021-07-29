import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from './auth-guard.service';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PensionDetailsComponent } from './pension-details/pension-details.component';
import { ProcessPensionComponent } from './process-pension/process-pension.component';
import { RestrictAccessGuard } from './restrict-access.guard';

const routes: Routes = [
  { path: "", pathMatch: "full", redirectTo: "/login" },
  { path: "login", component: LoginComponent},
  { path: "home", component: HomeComponent },
  { path: "process-pension", component: ProcessPensionComponent},//, canActivate: [AuthGuardService] },
  { path: "pension-details", component: PensionDetailsComponent},
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
