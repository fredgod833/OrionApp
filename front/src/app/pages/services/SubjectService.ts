import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from "../interface/Subject";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class SubjectService{
    
    private pathService = 'api/subject';

    constructor(private httpClient: HttpClient){};

    public getSubjectList(){
   
       const response =  this.httpClient.get<Subject[]>(`${this.pathService}/list`);
  
        console.log("Response", response);

        return response;
    }
}