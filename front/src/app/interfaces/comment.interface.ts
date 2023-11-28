export interface Comment {
  id: number;
  post: {
      id: number;
      title: string;
  }
  user: {
      id: number;
      name?: string;
  };
  content: string;
  created_at: string;
  updated_at: string;
}
