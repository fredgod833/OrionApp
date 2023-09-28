import { Route } from "@angular/router";
import Subject from "./subject/subject.component";
import menuBar from "../components/menu.component";
import Post from "./post/post.component";

export const MENU_ROUTER:Route[] = [
    {path: 'subject', component: Subject},
    {path: 'post', component: Post},
    {path: 'menu', component: menuBar}
];