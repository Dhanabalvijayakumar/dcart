import { Category } from "./Category";

export interface Product {
    productId: number;
    productName: string;
    description: string;
    price: number;
    brand: string;
    rating: number;
    discountPercentage: number;
    imageUrl: string;
    categoriesDTO: Category;
}