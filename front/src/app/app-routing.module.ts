import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import Subject from './pages/subject/subject.component';
import Post from './pages/post/post.component';
import menuBar from './components/menu.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './components/guards/auth.guard';
import PostSelected from './pages/post/comments/selected.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent},  
  {path: 'login', component: LoginComponent},
  {path: 'post', component: Post},
  {path: 'menu', component: menuBar},
  {path: 'comments', component: PostSelected},

  {
    path: 'menu', 
    canActivate: [AuthGuard],
    loadChildren: () => import('./pages/menu.module').then(m => m.MenuModule)}

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
