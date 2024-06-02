export interface User {
  id: number;
  email: string;
  login: string;
  admin: boolean;
  createdAt: Date;
  updatedAt?: Date;
}
