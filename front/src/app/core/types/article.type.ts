export type ArticleSummary = {
  id: number;
  title: string;
  date: string;
  username: string;
  description: string;
};

export type UserComment = {
  username: string;
  comment: string;
};

export type Article = {
  id: number;
  authorName: string;
  title: string;
  comments: Comment[];
};
