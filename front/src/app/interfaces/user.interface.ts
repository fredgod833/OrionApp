import { Topic } from './topic.interface';

export interface User {
  id: number;
  topics: Topic[];
  name: string;
  email: string;
  password?: string;
  created_at: string;
  updated_at: string;
}
