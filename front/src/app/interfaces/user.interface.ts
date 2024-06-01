export interface User {
  id: number;
  email: string;
  login: string;
  admin: boolean;
  password: string;
  createdAt: Date;
  updatedAt?: Date;
}
