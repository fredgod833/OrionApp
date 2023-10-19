import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from '@angular/common/http';
import { SubjectDto } from "../model/subjectdto";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class SubjectService{
    
    private pathService = 'api/subject';

    constructor(private httpClient: HttpClient){};
    // public token =  localStorage.getItem("token");
    public getSubjectList():Observable<SubjectDto[]>{

       return this.httpClient.get<SubjectDto[]>(`${this.pathService}/subjectDto_list`);
       
    }
}