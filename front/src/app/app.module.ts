import { NgModule } from '@angular/core';

// Angular Material Imports
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';


// Other Angular Import
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Interceptors
import { JwtInterceptor } from './interceptors/jwt.interceptor';

// Global Import
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Pages
import { HomeComponent } from './pages/home/home.component';
import { TopicsComponent } from './pages/topics/topics.component';
import { PostsComponent } from './pages/posts/posts.component';
import { PostDetailsComponent } from './pages/post-details/post-details.component';
import { MeComponent } from './pages/me/me.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';

// Components
import { TopicCardComponent } from './components/topic-card/topic-card.component';
import { LayoutComponent } from './components/layout/layout.component';
import { MobileMenuComponent } from './components/mobile-menu/mobile-menu.component';
import { CustomInputComponent } from './components/custom-input/custom-input.component';
import { PostCardComponent } from './components/post-card/post-card.component';
import { CustomTextareaComponent } from './components/custom-textarea/custom-textarea.component';
import { CommentCardComponent } from './components/comment-card/comment-card.component';
import { CustomSelectComponent } from './components/custom-select/custom-select.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { NavbarLoginComponent } from './components/navbar-login/navbar-login.component';

const materialModule = [
  MatButtonModule,
  MatToolbarModule,
  MatInputModule,
  MatCardModule,
  MatSidenavModule,
  MatProgressSpinnerModule,
  MatIconModule,
  MatSidenavModule
];

const pagesModule = [
 HomeComponent,
 LoginComponent,
 RegisterComponent,
 TopicsComponent,
 PostsComponent,
 PostDetailsComponent,
 CreatePostComponent,
 MeComponent
];

const componentModule = [
  LayoutComponent,
  NavbarComponent,
  PostCardComponent,
  TopicCardComponent,
  CommentCardComponent,
  MobileMenuComponent,
  CustomInputComponent
];

@NgModule({
  declarations: [
    AppComponent,
    ...pagesModule,
    ...componentModule,
    CustomSelectComponent,
    CustomTextareaComponent,
    NavbarLoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ...materialModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
