import {Theme} from "../features/themes/interfaces/theme";

export interface User {
	id: number,
	username: string,
	email: string,
	createdAt: Date,
	updatedAt: Date,
  subscriptions: Theme[]
}
