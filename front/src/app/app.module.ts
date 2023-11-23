import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { MatMenuModule } from '@angular/material/menu';
import Subject from './pages/subject/subject.component';
import { ReactiveFormsModule } from '@angular/forms';
import {MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import menuBar from './components/menu.component';
import PostSelected from './pages/post/comments/selected.component';
import Post from './pages/post/post.component';

@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    AppRoutingModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    MatMenuModule,
    RouterModule,
    Subject,
    PostSelected,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    menuBar,
    LoginComponent,
    Post
        ],

  bootstrap: [AppComponent]
})
export class AppModule {}
