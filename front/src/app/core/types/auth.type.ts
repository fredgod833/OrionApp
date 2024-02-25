export type RegisterRequest = {
  email: string;
  username: string;
  password: string;
};

export type LoginRequest = {
  identifier: string;
  password: string;
};
