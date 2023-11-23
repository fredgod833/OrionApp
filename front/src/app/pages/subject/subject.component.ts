import { Component, OnInit } from "@angular/core";
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { SubjectService } from "../services/subject.service";
import { SubjectDto } from "../model/subjectdto";
import { NgFor } from "@angular/common";
import { UserService } from "../services/user.service";
import AuthService from "../services/auth.component";

@Component({
    selector: 'app-subject',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.scss'],
    standalone: true,
    imports: [MatCardModule, MatButtonModule, NgFor],
})
export default class Subject implements OnInit{

    subject_list: SubjectDto[] = [];
    
    constructor(private pathService: SubjectService, private userService: UserService, private authService:AuthService){}
    ngOnInit(){
      this.getSubjectList()
    };

    getSubjectList(){
      this.pathService.getSubjectList().subscribe(response => {
        for(let i=0; i < response.length; i++){

          this.subject_list.push(response[i]);
        }
      })

    }

    subscribe(idSubject: number){
      
     return this.userService.subscribe(idSubject);
          
    }
}