export interface Post {
  id: number;
  title: string;
  content: string;
  userId?: number;
  topicId?: number;
  topicName?: string;
  username?: string;
  createdAt: string;
  updatedAt: string;
}
