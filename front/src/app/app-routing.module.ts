import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {AuthGuard} from "./guards/auth.guard";
import {UserComponent} from "./pages/user/user.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'user/account',
    canActivate: [AuthGuard],
    component: UserComponent
  } ,
  {
    path: 'topics',
    canActivate: [AuthGuard],
    loadChildren: () => import('./pages/topic/topic.module').then(m => m.TopicModule)
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    loadChildren: () => import('./pages/posts/posts.module').then(m => m.PostsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
