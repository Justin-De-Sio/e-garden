import type {User} from "~/model/User";

export interface Reports {
    id?: bigint,
    reportDate: string,
    content: string,
    validated: boolean,
    createdAt: string,
    user: User
}