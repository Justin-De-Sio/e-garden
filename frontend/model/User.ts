
export interface User {
    id?: bigint,
    name: string,
    surname: string,
    email: string,
    role: "ADMINISTRATEUR" | "UTILISATEUR" | "RESPONSABLE";
    className: string,
    groupNumber: number
    locked: boolean
}
