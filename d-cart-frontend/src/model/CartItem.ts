import { Product } from "./Product";

export interface CartItem {
    cartItemId: number;
    quantity: number;
    productsDTO: Product;
}