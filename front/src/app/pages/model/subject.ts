import PostInterface from "./post";

export default interface Subject{
idSubject: number,
title: string,
description: string,
isSubscribed: boolean,
postList:PostInterface[];

}