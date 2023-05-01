import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthPortalComponent } from './portal/auth-portal.component';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppIconComponent } from 'src/app/core/components/app-icon/app-icon.component';
import { MatButtonModule } from '@angular/material/button';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        RouterModule,
    ],
    exports: [],
    declarations: [
        AuthPortalComponent,
        LoginComponent,
        RegisterComponent,
        AppIconComponent,
    ],
    providers: [],
})

export class AuthPortalModule { }
