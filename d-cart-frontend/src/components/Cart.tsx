import { useEffect, useState } from "react";
import { addToCart, getCartItems, placeOrder, removeFromCart } from "../api/CartProdAPI";
import { Link } from "react-router-dom";
import { CartItem } from "../model/CartItem";

const Cart = () => {

    const [cartItems, setCartItems] = useState<CartItem[]>([]);

    useEffect(() => {
        fetchCartItems();
    }, []);

    const fetchCartItems = async () => {
        try {
            const response = await getCartItems();
            console.log(response);
            setCartItems(response);
        }
        catch (err) {
            console.log(err);
        }
    }

    // const totalAmount = cartItems.reduce(
    //     (total, item) =>
    //         total + (item.productsDTO.price * item.quantity), 0
    // );

    const totalAmount = cartItems.reduce(
        (total, item) =>
            total + Math.round(
                item.productsDTO.price -
                (item.productsDTO.price * item.productsDTO.discountPercentage) / 100
            ), 0
    );

    const handleAddToCart = async (productId: number) => {
        try {
            const userId = localStorage.getItem("userId");
            // console.log(userId);
            const cartData = {
                "userId": Number(userId),
                "productId": productId
            };
            const response = await addToCart(cartData);
            await fetchCartItems();
            console.log(response);
            // alert(response);
        }
        catch (err) {
            console.log(err);
        }
    }

    const handleDeleteFromCart = async (productId: number) => {
        try {
            const userId = localStorage.getItem("userId");

            const cartData = {
                "userId": Number(userId),
                "productId": productId
            };
            const response = await removeFromCart(cartData);
            await fetchCartItems();
            console.log(response);
            // alert(response);
        }
        catch (err) {
            console.log(err);
        }
    }

    const handlePlaceOrder = async () => {
        try {
            const response = await placeOrder();
            alert(response);
            await fetchCartItems();
        }
        catch (err) {
            alert(err);
            setCartItems([]);
        }
    }

    return (
        <>

            <style>
                {`
                    body {
                        background-color: #f5f7fb;
                    }
                    .product-card {
                        transition: all 0.4s ease;
                        cursor: pointer;
                    }
                    .product-card:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 10px 20px rgba(0,0,0,0.12) !important;
                    }
                `}
            </style>

            <div className="container py-5">
                {
                    cartItems.length === 0 ? (
                        <div className="d-flex flex-column justify-content-center align-items-center"
                            style={{ minHeight: "70vh" }} >
                            <h2 className="fw-bold">Your cart is empty 🛒</h2>
                            <p className="text-muted fs-5">Add products to cart</p>
                            <Link to="/products"
                                className="btn btn-primary rounded-pill px-4" >
                                Continue shopping
                            </Link>
                        </div>
                    ) : (
                        <>
                            <h1 className="fw-bold text-center mb-5">Shopping Cart</h1>
                            {
                                cartItems.map((item) => (
                                    <div>
                                        <div className="card product-card shadow-sm border-0 rounded-4 mb-4 p-3" key={item.cartItemId}>
                                            <div className="row align-items-center">
                                                <div className="col-md-3 text-center">
                                                    <img src={item.productsDTO.imageUrl}
                                                        alt={item.productsDTO.productName}
                                                        className="img-fluid"
                                                        style={{
                                                            objectFit: "contain"
                                                        }} />
                                                </div>
                                                <div className="col-md-6">
                                                    <h3 className="fw-bold">{item.productsDTO.productName}</h3>
                                                    <p className="text-muted">
                                                        Brand: <span className="fw-semibold">{item.productsDTO.brand}</span>
                                                    </p>
                                                    <p className="mb-2 small text-warning">
                                                        ⭐ {item.productsDTO.rating}
                                                    </p>
                                                    <p className="text-muted">{item.productsDTO.description}</p>
                                                    <div className="d-flex align-items-center gap-2 mb-2">
                                                        <h5 className="text-success fw-bold m-0">
                                                            ₹ {Math.round(
                                                                item.productsDTO.price -
                                                                (item.productsDTO.price * item.productsDTO.discountPercentage) / 100
                                                            )}
                                                        </h5>
                                                        <small className="text-muted text-decoration-line-through">
                                                            ₹ {item.productsDTO.price}
                                                        </small>
                                                        <small className="text-danger fw-bold">
                                                            {item.productsDTO.discountPercentage}% OFF
                                                        </small>
                                                    </div>
                                                    {/* <p className="mt-2">{item.quantity}</p> */}
                                                </div>
                                                <div className="col-md-3 text-center">
                                                    <span className="btn btn-outline-danger rounded-pill" onClick={() => handleDeleteFromCart(item.productsDTO.productId)}>-</span>
                                                    <span className="btn">{item.quantity}</span>
                                                    <span className="btn btn-outline-success rounded-pill" onClick={() => handleAddToCart(item.productsDTO.productId)}>+</span>
                                                </div>
                                            </div>
                                        </div>
                                        <hr />
                                    </div>
                                ))
                            }

                            <div className="card shadow border-0 rounded-4 p-4 mt-5">
                                <div className="d-flex justify-content-between align-items-center">
                                    <h3 className="fw-bold">Total</h3>
                                    <h3 className="fw-bold text-success">₹ {totalAmount}</h3>
                                </div>
                                <button className="btn btn-warning btn-lg rounded-pill mt-4"
                                    onClick={handlePlaceOrder} >Place Order</button>
                            </div>

                        </>
                    )
                }
            </div>
        </>
    )
}

export default Cart;