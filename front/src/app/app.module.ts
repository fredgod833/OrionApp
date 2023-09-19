import { NgModule } from '@angular/core';
import {CommonModule} from "@angular/common";
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import {AuthModule} from "./auth/auth.module";
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {TopicModule} from "./pages/topic/topic.module";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import { UserComponent } from './pages/user/user.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {PostsModule} from "./pages/posts/posts.module";

const materialModule = [
  MatFormFieldModule,
  MatIconModule,
  MatCardModule,
  MatButtonModule,
  MatInputModule,
  MatToolbarModule,
  MatTooltipModule,
  MatSnackBarModule
]

@NgModule({
  declarations: [AppComponent, HomeComponent, UserComponent],
    imports: [
        CommonModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        AuthModule,
        TopicModule,
        PostsModule,
        HttpClientModule,
        ReactiveFormsModule,
        ...materialModule
    ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
