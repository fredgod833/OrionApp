import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthPortalComponent } from './portal/auth-portal.component';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/core/components/shared.module';

@NgModule({
    imports: [
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        RouterModule,
        SharedModule,
    ],
    exports: [],
    declarations: [
        AuthPortalComponent,
        LoginComponent,
        RegisterComponent
    ],
    providers: [],
})

export class AuthPortalModule { }
