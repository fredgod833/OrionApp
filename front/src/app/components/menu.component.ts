import { Component } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import Subject from "../pages/subject/subject.component";
import Post from "../pages/post/post.component";
import { NgClass, NgIf } from "@angular/common";
import CreatePost from "../pages/post/create/post.create";
import ButtonCreation from "../pages/post/button/button.post.create";
import UserInformation from "../pages/user/user.information";

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.scss'],
    standalone: true,
    imports: [RouterModule, Subject, Post, NgIf, CreatePost, ButtonCreation, NgClass, UserInformation]
})

export default class menuBar{

  isButtonCreationActive = false;

  constructor(private router: Router){}

  //Redirect router to post
  activePostLink():void{
    this.router.navigate(['post']);
  }

  //Redirect router to subject
  activeSubjectLink():void{
    this.router.navigate(['subject']);
  }

   //Redirect router to user information=
  activeUserInformation():void{
    this.router.navigate(['user-information']);
  }
  // Hidde button when its actived
  hiddenButton():void{
      this.isButtonCreationActive = true;
  }
}