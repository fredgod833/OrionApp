import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import PostInterface from "../model/post";
import PostCreate from "../model/post.create";
import { FormControl } from "@angular/forms";
// import { Token } from "@angular/compiler";

@Injectable({
    providedIn: 'root'
})
export class PostService{

    private pathService = 'api/post';
    id_subject!:number;
    // private token!: Token;


    constructor(private httClient: HttpClient){};

    public getPostList():Observable<PostInterface[]>{

         return this.httClient.get<PostInterface[]>(`${this.pathService}/post_list`);
        }

    public createPost(postCreate: PostCreate, id_subject: number):Observable<PostCreate>{

        return this.httClient.post<PostCreate>(`${this.pathService}/create_post/${id_subject}`, postCreate);
    }
}