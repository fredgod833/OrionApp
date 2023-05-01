import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/core/models/auth/requests/register-request.interface';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
    selector: 'app-register',
    templateUrl: 'register.component.html',
    styleUrls:[
        'register.component.scss'
    ]
})

export class RegisterComponent implements OnInit {


    public readonly registerForm = this.fb.group({
        username: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]]
    });

    constructor(
        private fb: FormBuilder,
        private auth: AuthService,
        private router: Router
    ) { }

    ngOnInit() { }

    public usernameControl(){
        return this.registerForm.get('username');
    }

    public emailControl(){
        return this.registerForm.get('email');
    }

    public passwordControl(){
        return this.registerForm.get('password');
    }

    public handleSubmit(){
        const registerReq: RegisterRequest = {
            username: this.registerForm.value.username!,
            email: this.registerForm.value.email!,
            password: this.registerForm.value.password!
        }

        this.auth.register(registerReq).then(logged => {
            if(!logged){ return; }
            this.router.navigateByUrl('/home');
        })
    }
}