import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { SubjectDto } from "../model/subjectdto";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class SubjectService{
    
    private pathService = 'api/subject';

    constructor(private httpClient: HttpClient){};

    public getSubjectList():Observable<SubjectDto[]>{
   
       const response: Observable<SubjectDto[]> =  this.httpClient.get<SubjectDto[]>(`${this.pathService}/subjectDto_list`);

        return response;
    }
}