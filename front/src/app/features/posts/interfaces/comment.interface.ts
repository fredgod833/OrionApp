import {User} from "../../../interfaces/user.interface";

export interface Commentary {
  id: number;
  postId: number;
  user: User;
  content: string;
  createdAt: Date;
  updatedAt: Date;
}
