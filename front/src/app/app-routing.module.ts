import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthPortalComponent } from './pages/auth-portal/portal/auth-portal.component';
import { LoginComponent } from './pages/auth-portal/login/login.component';
import { RegisterComponent } from './pages/auth-portal/register/register.component';
import { HomeComponent } from './pages/home/home/home.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: AuthPortalComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
