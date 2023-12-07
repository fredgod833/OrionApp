

export default interface User{
    id_user: number,
    username: string,
    email: string,
    subscription: {
        id_subscription: number,
        subjectList: {
            idSubject: number,
            title: string,
            description: string,
            isSubscribed: boolean
        }[]
    }
}