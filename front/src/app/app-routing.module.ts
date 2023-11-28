import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { PostsComponent } from './pages/posts/posts.component';
import { PostDetailsComponent } from './pages/post-details/post-details.component';
import { MeComponent } from './pages/me/me.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '',  component: HomeComponent },
  { path : 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},

  { path: 'me',
    canActivate: [AuthGuard.canActivate],
    component: MeComponent
  },
  {
    path: 'posts',
    canActivate: [AuthGuard.canActivate],
    component: PostsComponent
  },
  {
    path: 'posts/new',
    canActivate: [AuthGuard.canActivate],
    component: CreatePostComponent
  },
  { path: 'posts/:id',
    canActivate: [AuthGuard.canActivate],
    component: PostDetailsComponent
  },
  {
    path: 'topics',
    canActivate: [AuthGuard.canActivate],
    component: TopicsComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
