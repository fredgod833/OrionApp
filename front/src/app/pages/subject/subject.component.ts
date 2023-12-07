import { Component, OnInit } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { SubjectService } from "../services/subject.service";
import { SubjectDto } from "../model/subjectdto";
import { NgFor, NgIf } from "@angular/common";
import { UserService } from "../services/user.service";
import AuthService from "../services/auth.component";

@Component({
    selector: 'app-subject',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatButtonModule, NgFor, NgIf],
})
export default class Subject implements OnInit{

    subject_list: SubjectDto[] = [];
    isSubscribed =  false;

    constructor(private pathService: SubjectService, private userService: UserService, private authService:AuthService){}
    
    ngOnInit(){
      this.getSubjectList()
    };

    getSubjectList(){
      this.pathService.getSubjectList().subscribe(response => {
        for(let i=0; i < response.length; i++){

          this.subject_list.push(response[i]);
          console.log("Subject list: ",this.subject_list);
        }
      })

    }

    subscribe(idSubject: number){

      if(idSubject != 0){
       // Subscribe subject
       this.userService.subscribe(idSubject);
        
       //Loop to find the right subject
       this.subject_list.map(val => {

        //Set subscribed to true to the right subject
        if(idSubject == val.idSubject){
         val.isSubscribed = true;
        
        }
      
      })
        }
    }
}