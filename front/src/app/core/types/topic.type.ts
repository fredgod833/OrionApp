export type Topic = {
  id: number;
  userId: number;
  themeId: number;
  title: string;
  description: string;
  isSubscribed: boolean;
};

export type TopicOptions = {
  id: number;
  theme: string;
};
