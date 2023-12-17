import { Component, OnInit } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import AuthService from "../services/auth.component";
import User from "src/app/interfaces/user.interface";
import { SubjectDto } from "../model/subjectdto";
import { NgFor } from "@angular/common";
import { UserService } from "../services/user.service";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";

@Component({
    selector: 'user-info',
    templateUrl: './user.information.html',
    styleUrls: ['./user.information.scss'],
    standalone: true,
    imports: [MatFormFieldModule, MatCardModule, NgFor, ReactiveFormsModule]
})
export default class UserInformation implements OnInit{
    public subjectList: SubjectDto[] = [];
    public form_profil!: FormGroup;
    
    //Properties to stock subscriptions
    public subscriptionOfMeForProfil!:Subscription;
    public subscriptionOfMeForUser!:Subscription;
    public subscriptionOfUser!:Subscription;
    public subscriptionOfLogOut!:Subscription;
    
    constructor(private formBuilder:FormBuilder, private authService: AuthService, private userService: UserService, 
    private router: Router){}
    
    //Initialization
    ngOnInit(): void {
        this.initFormProfil();
        this.userProfil();
    }

    //Unsubscribe
    unsubscribe(idSubject:number){
        return this.userService.unsubscribe(idSubject);
    }

    //Push subscribed subjects for list of subjects
    public userProfil():void{

        this.authService.me().subscribe({
            next:(value)=> {
                if(value.subscription != null && value.subscription.subjectList.length > 0){
                    value.subscription.subjectList.map(subscribed => {
                        this.subjectList.push(subscribed);
                    });
                }
            },
        })
    }

    //Change username, email and return a message
    public changeUserUsernameAndEmail():void{
        console.log(this.form_profil.value)
        this.authService.me().subscribe({next:(value)=> {

            const usernameAndEmail  = this.form_profil.value as User;
            value.username = usernameAndEmail.username;
            value.email = usernameAndEmail.email;

            this.userService.changeUserUsernameAndEmail(value).subscribe({
                next(){
                    console.log("Username and Email changed: ", value);
                    return "Username and Email changed: ";
                }
            })
        },});
    }

    //Log user out
    public logOut():void{
        this.authService.logout().subscribe({
            next:()=> {
                console.log("Logout sucessfull");
                this.router.navigate(['/']);
            },
        });
    }

    //Unsubscribe subscriptions
    ngOnDestroy():void{
        if(this.subscriptionOfLogOut){
            this.subscriptionOfLogOut.unsubscribe();
        }
        if(this.subscriptionOfMeForProfil){
            this.subscriptionOfMeForProfil.unsubscribe();
        }
        if(this.subscriptionOfMeForUser){
            this.subscriptionOfMeForUser.unsubscribe();
        }
        if(this.subscriptionOfUser){
            this.subscriptionOfUser.unsubscribe();
        }
    }

    //Form required fields
    public initFormProfil():void{
        this.form_profil = this.formBuilder.group({
            username: ["", [Validators.required]],
            email: ["", [Validators.required]]
        })
    }
}