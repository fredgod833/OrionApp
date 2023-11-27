import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { SessionService } from "../services/session.service";
import Subject from "../pages/subject/subject.component";
import Post from "../pages/post/post.component";
import { NgClass, NgIf } from "@angular/common";
import CreatePost from "../pages/post/create/post.create";
import ButtonCreation from "../pages/post/button/button.post.create";
import UserInformation from "../pages/user/user.information";


@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls:['./menu.component.scss'],
    standalone:true,
    imports: [RouterModule, Subject, Post, NgIf, CreatePost, ButtonCreation, NgClass, UserInformation],
  })

export default class menuBar{

  isPostLinkActive = false;
  isSubjectLinkActive = false;
  isPostCreationActive = false;
  isButtonCreationActive = false;
  isUserInformationActive = false;

  constructor(private sessionService: SessionService){

  }

  activePostLink(){
    this.sessionService.isLogged;
    this.isPostLinkActive = true;
    this.isSubjectLinkActive = false;

  }
  activeSubjectLink(){
    this.isSubjectLinkActive = true;
    this.isPostLinkActive = false;
  }

  activePostCreation(){
    this.isPostCreationActive = true;
    this.isPostLinkActive = false;
  }

  activeUserInformation(){
    this.isUserInformationActive = true;
  }

  hiddenButton(){
      this.isButtonCreationActive = true;
  }

}