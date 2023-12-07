import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import PostInterface from "../model/post";
import PostCreate from "../model/post.create";

@Injectable({
    providedIn: 'root'
})
export class PostService{

    private pathService = 'api/post';
    //Stock subject identity
    id_subject!:number;

    constructor(private httClient: HttpClient){};

    //Return a list of post
    public getPostList():Observable<PostInterface[]>{

         return this.httClient.get<PostInterface[]>(`${this.pathService}/post_list`);
        }
    
    //Create a post
    public createPost(postCreate: PostCreate, id_subject: number):Observable<PostCreate>{
        return this.httClient.post<PostCreate>(`${this.pathService}/create_post/${id_subject}`, postCreate);
    }
}