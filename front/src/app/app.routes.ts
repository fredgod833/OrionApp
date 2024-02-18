import { Routes } from '@angular/router';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  /*
  {
    path: 'register',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  {
    path: 'login',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  {
    path: 'articles',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  {
    path: 'article/:id',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  {
    path: 'topics',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  {
    path: 'user',
    title: 'Monde de Dév',
    component: HomeComponent,
  },
  */
  {
    path: '**',
    title: 'Error 404, page not found!',
    component: NotFoundComponent,
  },
];
