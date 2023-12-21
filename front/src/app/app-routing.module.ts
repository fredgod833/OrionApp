import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import menuBar from './components/menu.component';
import { LoginComponent } from './pages/login/login.component';
import PostSelected from './pages/post/comments/selected.component';
import Register from './pages/register/register.component';
import CreatePost from './pages/post/create/post.create';
import Post from './pages/post/post.component';
import Subject from './pages/subject/subject.component';
import UserInformation from './pages/user/user.information';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent},  
  {path: 'login', component: LoginComponent},
  {path: 'menu', component: menuBar},
  {path: 'comments', component: PostSelected},
  {path: 'inscription', component: Register},
  {path: 'post-creation', component: CreatePost},
  {path: 'post', component: Post},
  {path: 'subject', component: Subject},
  {path: 'user-information', component: UserInformation}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
