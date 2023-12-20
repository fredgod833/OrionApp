import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
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

  // Conditions for template control
  isPostLinkActive = false;
  isSubjectLinkActive = false;
  isPostCreationActive = false;
  isButtonCreationActive = false;
  isUserInformationActive = false;

  constructor(){}
  

  //Enable post page and Unable others
  activePostLink():void{
    this.isPostLinkActive = true;
    this.isSubjectLinkActive = false;
    this.isUserInformationActive = false;
    this.isPostCreationActive = false;
  }

  //Enable subject page and Unable others
  activeSubjectLink():void{
    this.isSubjectLinkActive = true;
    this.isPostLinkActive = false;
    this.isUserInformationActive = false;
    this.isPostCreationActive = false;
  }
  //Enable post creation page and Unable others
  activePostCreation():void{
    this.isPostCreationActive = true;
    this.isPostLinkActive = false;
    this.isUserInformationActive = false;
    this.isSubjectLinkActive = false;
  }
  //Enable user information page and Unable others
  activeUserInformation():void{
    this.isSubjectLinkActive = false;
    this.isPostLinkActive = false;
    this.isPostCreationActive = false;
    this.isUserInformationActive = true;
  }
  // Hidde button when its actived
  hiddenButton():void{
      this.isButtonCreationActive = true;
  }
}