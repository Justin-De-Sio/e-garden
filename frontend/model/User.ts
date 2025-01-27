import type {Roles} from "./Roles"

export interface User {
    answerId?: bigint,
    name: string,
    surname: string,
    email: string,
    role: Roles
    className: string,
    groupNumber: number
}