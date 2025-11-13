import {LOCALE_ID, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { MeComponent } from './features/user/pages/me/me.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import {DatePipe} from '@angular/common';
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FlexModule} from "@angular/flex-layout";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RegisterComponent } from './features/auth/pages/register/register.component';
import { LoginComponent } from './features/auth/pages/login/login.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import { MainComponent } from './pages/main/main.component';
import { PostPreviewComponent } from './features/posts/components/post-preview/post-preview.component';
import { TopicPreviewComponent } from './features/topics/components/topic-preview/topic-preview.component';
import { TopicListComponent } from './features/topics/components/topic-list-view/topic-list.component';
import { PostsReaderComponent } from './features/posts/pages/posts-reader/posts-reader.component';
import { PostsEditorComponent } from './features/posts/pages/posts-editor/posts-editor.component';
import { TopicsPageComponent } from './features/topics/pages/topics-page/topics-page.component';
import { PostSelectComponent } from './features/posts/pages/post-select/post-select.component';
import {MatSelectModule} from "@angular/material/select";
import { CommentaryEditorComponent } from './features/posts/components/commentary-editor/commentary-editor.component';
import { CommentaryViewComponent } from './features/posts/components/commentary-view/commentary-view.component';
import { CommentaryListComponent } from './features/posts/components/commentary-list/commentary-list.component';
import {MatMenuModule} from "@angular/material/menu";
import '@angular/common/locales/global/fr';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NotFoundComponent,
    MeComponent,
    RegisterComponent,
    LoginComponent,
    MainComponent,
    PostPreviewComponent,
    TopicPreviewComponent,
    TopicListComponent,
    PostsReaderComponent,
    PostsEditorComponent,
    TopicsPageComponent,
    PostSelectComponent,
    CommentaryEditorComponent,
    CommentaryViewComponent,
    CommentaryListComponent],

    imports: [
        AppRoutingModule,
        HttpClientModule,
        BrowserModule,
        BrowserAnimationsModule,
        DatePipe,
        MatToolbarModule,
        MatCardModule,
        MatIconModule,
        MatInputModule,
        MatButtonModule,
        MatFormFieldModule,
        FlexModule,
        FlexLayoutModule,
        FormsModule,
        ReactiveFormsModule,
        MatSelectModule,
        MatMenuModule
    ],

  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: LOCALE_ID, useValue: 'fr' }
  ],

  bootstrap: [AppComponent]
})
export class AppModule {}
