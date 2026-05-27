import { Orders } from "./Orders";
import { Product } from "./Product";

export interface OrderItems {
    orderItemId: number;
    quantity: number;
    price: number;
    ordersDTO: Orders;
    productsDTO: Product;
}