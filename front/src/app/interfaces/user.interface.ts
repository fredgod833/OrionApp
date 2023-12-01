

export default interface User{
    id_user: number,
    username: string,
    email: string,
    subscription: {
        id_subscription: number,
        subjectList: {
            id_subject: number,
            title: string,
            description: string,
        }[]
    }
}