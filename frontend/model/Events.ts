import type {Door} from "~/model/Door";

export interface Events {
    id?: bigint,
    title: string,
    description: string,
    eventType: number,
    userId: number,
    door: Door,
}