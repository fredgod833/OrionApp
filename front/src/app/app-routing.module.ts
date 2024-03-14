import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {AuthGuard} from "./guards/auth.guard";
import {UnAuthGuard} from "./guards/unAuth.guard";
import {UserDetailsComponent} from "./pages/account/user-details/user-details.component";
import {AccountGuard} from "./guards/account.guard";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {
    path: '',
    canActivate: [UnAuthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/posts/post.module').then(m => m.PostModule)
  },
  {
    path: 'themes',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/themes/theme.module').then(m => m.ThemeModule)
  },
  {
    path: 'user',
    canActivate: [AuthGuard],
    canDeactivate: [AccountGuard],
    component: UserDetailsComponent
  },
  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
