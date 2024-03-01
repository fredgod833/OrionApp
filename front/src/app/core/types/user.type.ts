export type UserEntity = {
  id: number;
  username: string;
  email: string;
  createdAt: string;
  updatedAt: string;
};

export type UserInfo = {
  token: string | null;
  id: number | null;
  username: string | null;
  email: string | null;
};

export type UserBasicInfo = Omit<UserInfo, 'token'>;
