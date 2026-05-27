import { UserData } from "./UserData";

export interface Orders {
    orderId: number;
    totalPrice: number;
    status: string;
    createdTime: string;
    usersDTO: UserData;
}