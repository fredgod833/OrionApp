import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './guards/auth.guard';
import {UnauthGuard} from './guards/unauth.guard';
import {MeComponent} from "./components/me/me.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {NgModule} from "@angular/core";
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
//const routes: Routes = [{ path: '', component: HomeComponent }];

const routes: Routes = [
  {
    title: 'MDD',
    path: '',
    canActivate: [UnauthGuard],
    component: HomeComponent
    // loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    title: 'Login',
    path: 'login',
    canActivate: [UnauthGuard],
    component: LoginComponent
    // loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    title: 'Register',
    path: 'register',
    canActivate: [UnauthGuard],
    component: RegisterComponent
    // loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'me',
    canActivate: [AuthGuard],
    component: MeComponent
  },
  {
    path: 'articles',
    canActivate: [AuthGuard],
    component: MeComponent
  },
  {
    path: 'themes',
    canActivate: [AuthGuard],
    component: MeComponent
  },
  { path: '404', component: NotFoundComponent },
  { path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {

}
