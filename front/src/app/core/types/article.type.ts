export type ArticleSummary = {
  id: number;
  articleId: number;
  title: string;
  username: string;
  description: string;
  publicationDate: string;
};

export type UserComment = {
  username: string;
  comment: string;
};

export type Article = {
  id: number;
  title: string;
  description: string;
  authorName: string;
  comments: UserComment[];
  theme: string;
  creationDate: string;
};
