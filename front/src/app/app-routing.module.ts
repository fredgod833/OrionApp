import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './guards/auth.guard';
import {UnauthGuard} from './guards/unauth.guard';
import {MeComponent} from "./features/user/pages/me/me.component";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {NgModule} from "@angular/core";
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./features/auth/pages/login/login.component";
import {RegisterComponent} from "./features/auth/pages/register/register.component";
import {TopicsPageComponent} from "./features/topics/pages/topics-page/topics-page.component";
import {PostsEditorComponent} from "./features/posts/pages/posts-editor/posts-editor.component";
import {PostSelectComponent} from "./features/posts/pages/post-select/post-select.component";
import {PostsReaderComponent} from "./features/posts/pages/posts-reader/posts-reader.component";

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
    path: 'posts',
    canActivate: [AuthGuard],
    component: PostSelectComponent
  },
  {
    path: 'post/view',
    canActivate: [AuthGuard],
    component: PostsReaderComponent
  },
  {
    path: 'post/new',
    canActivate: [AuthGuard],
    component: PostsEditorComponent
  },
  {
    path: 'themes',
    canActivate: [AuthGuard],
    component: TopicsPageComponent,
  },
  {
    path: 'post-editor',
    canActivate: [AuthGuard],
    component: PostsEditorComponent,
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
