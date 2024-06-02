import {Topic} from "../../topics/interfaces/topic.interface";
import {User} from "../../../interfaces/user.interface";

export interface Post {
  id: number;
  topic: Topic;
  author: User;
  title: string;
  content: string;
  createdAt: Date;
  updatedAt: Date;
}
