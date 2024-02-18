import { Routes } from '@angular/router';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { HomeComponent } from './pages/home/home.component';
import { ArticleComponent } from './pages/article/article.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { UserComponent } from './pages/user/user.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';

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
  {
    path: 'register',
    title: `Page d'enregistrement`,
    component: RegisterComponent,
  },
  {
    path: 'login',
    title: 'Page de connexion',
    component: LoginComponent,
  },
  {
    path: 'articles',
    title: `Page d'articles`,
    component: ArticlesComponent,
  },
  {
    path: 'create-articles',
    title: `Page de création d'articles`,
    component: CreateArticleComponent,
  },
  {
    path: 'article/:id',
    title: `Page de l'article`,
    component: ArticleComponent,
  },
  {
    path: 'topics',
    title: 'Thèmes',
    component: TopicsComponent,
  },
  {
    path: 'user',
    title: 'Profil utilisateur',
    component: UserComponent,
  },
  {
    path: '**',
    title: 'Error 404, page not found!',
    component: NotFoundComponent,
  },
];
