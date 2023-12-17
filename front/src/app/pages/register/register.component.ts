import { NgIf } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatCardModule } from "@angular/material/card";
import AuthService from "../services/auth.component";
import RegisterRequest from "src/app/security/interfaces/register.component";
import { MatFormFieldModule } from "@angular/material/form-field";
import { Subscription } from "rxjs";

@Component({
    selector: 'register-page',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
    standalone: true,
    imports:[ReactiveFormsModule, MatCardModule, NgIf,  MatFormFieldModule]
})
export default class Register implements OnInit{
    public onError = false;
    public form!: FormGroup;

    public subscription!: Subscription;

    constructor(private formBuilder: FormBuilder, private authService: AuthService){}

    //Initialization
    ngOnInit():void{
        this.initForm();
    }

    //Register a user as authentified and return a message
    public register(){
        const register = this.form.value as RegisterRequest;
        this.subscription = this.authService.register(register).subscribe!({
            next(value) {
                console.log("User Registered!!!", value);
                return "User Registered!!!";
               
            },
            //Error set true for template
            error:()=> this.onError = true
        });

        return this.subscription;
    }
    
    //Unsubscribe subscription
    ngOnDestroy():void{
        if(this.subscription){
            this.subscription.unsubscribe();
        }
    }

    //Request required field for register validation
    public initForm():void{
        //Stock form fields
        this.form = this.formBuilder.group({
            username: ["", [Validators.required]],
            email: ["", [Validators.required]],
            password: ["" ,[Validators.required]]
        })
    }
}