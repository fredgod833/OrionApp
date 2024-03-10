import {Theme} from "../features/articles/interfaces/theme";

export interface User {
	id: number,
	username: string,
	email: string,
	createdAt: Date,
	updatedAt: Date,
  subscriptions: Theme[]
}
