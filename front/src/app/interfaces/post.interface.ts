export interface Post {
  id: number;
  topic: {
      id: number;
      title?: string;
  }
  user: {
      id: number;
      name?: string;
  }
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
}
