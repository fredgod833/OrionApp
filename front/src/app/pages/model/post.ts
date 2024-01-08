import Comments from "./comments";

export default interface PostInterface{
    id_post: number,
    title: string,
    date: Date;
    author: string,
    content: string,
    comments:Comments[]
};