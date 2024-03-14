import {NgModule} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCardModule} from "@angular/material/card";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {NotFoundComponent} from "./pages/not-found/not-found.component";
import {UserDetailsComponent} from './pages/account/user-details/user-details.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "./app-routing.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {NavbarComponent} from "./pages/shared/navbar/navbar.component";
import {SidenavListComponent} from "./pages/shared/sidenav-list/sidenav-list.component";

const materialModule = [
  MatCardModule,
  MatCardModule,
  MatButtonModule,
  MatSidenavModule,
  MatListModule,
  MatIconModule,
  MatToolbarModule,
  MatInputModule,
  MatSnackBarModule
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    SidenavListComponent,
    NotFoundComponent,
    UserDetailsComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    HttpClientModule,
    ReactiveFormsModule,
    ...materialModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
