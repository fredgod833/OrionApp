import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './components/topics/topics.component';
import { PostsComponent } from './components/posts/posts.component';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import { MeComponent } from './components/me/me.component';
import { CreatePostComponent } from './components/create-post/create-post.component'; // Ajouté pour utiliser le composant 'CreatePostComponent'.

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'topics', component: TopicsComponent },
  { path: 'posts', component: PostsComponent },
  { path: 'create-post', component: CreatePostComponent },
  { path: 'posts/:id', component: PostDetailsComponent },
  { path: 'me', component: MeComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
