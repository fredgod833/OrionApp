export type Topic = {
  id: number;
  title: string;
  description: string;
  isSubscribed: boolean;
};

export type TopicOptions = {
  id: number;
  theme: string;
};

export type TopicSubscription = {
  themeId: number;
  isSubscribed: boolean;
};
