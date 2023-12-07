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

    //Return subjects subscribed from user
    public userProfil(){

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

    public changeUserUsernameAndEmail(){
        console.log(this.form_profil.value)
        this.authService.me().subscribe({next:(value)=> {

            const usernameAndEmail  = this.form_profil.value as User;
            value.username = usernameAndEmail.username;
            value.email = usernameAndEmail.email;

            this.userService.changeUserUsernameAndEmail(value).subscribe({
                next(){
                    console.log("Username and Email changed: ", value);
                }
            })
        },});
    }

    public logOut(){
        this.authService.logout().subscribe({
            next:()=> {
                console.log("Logout sucessfull");
                this.router.navigate(['/']);
            },
        });
    }
    //Form required fields
    public initFormProfil(){
        this.form_profil = this.formBuilder.group({
            username: ["", [Validators.required]],
            email: ["", [Validators.required]]
        })
    }
}