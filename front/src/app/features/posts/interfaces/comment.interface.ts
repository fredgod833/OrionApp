import {User} from "../../../interfaces/user.interface";

export interface Comment {
  id: number;
  postId: number;
  author: User;
  content: string;
  createdAt: Date;
  updatedAt: Date;
}
