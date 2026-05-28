import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Product } from "../model/Product";
import { getProductById } from "../api/ProductsAPI";
import { addToCart, placeDirectOrder } from "../api/CartProdAPI";
import { addToWishlist, removeFromWishlist } from "../api/WishlistAPI";

const ProductDetails = () => {

    const { id } = useParams();

    const productId = Number(id);

    const [product, setProduct] = useState<Product>();

    const [addedItems, setAddedItems] = useState<number[]>([]);

    const [wishlistItems, setWishlistItems] = useState<number[]>([]);


    const fetchProduct = async () => {

        try {

            const response = await getProductById(productId);
            setProduct(response);

        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchProduct();
    }, []);

    const handleAddToCart = async (productId: number) => {
        try {
            const userId = localStorage.getItem("userId");
            // console.log(userId);
            const cartData = {
                "userId": Number(userId),
                "productId": productId
            };
            const response = await addToCart(cartData);
            // alert(response);
            console.log(response);
            setAddedItems((prev) => [...prev, productId])

            setTimeout(() => {
                setAddedItems([]);
            }, 3000);
        }
        catch (err) {
            console.log(err)
        }
    }

    const handleWishlist = async (productId: number) => {
        const userId = localStorage.getItem("userId");
        const wishlistData = {
            "userId": Number(userId),
            "productId": productId
        };

        const alreadyInWishlist = wishlistItems.includes(productId);

        if (!alreadyInWishlist) {
            try {
                const response = await addToWishlist(wishlistData);
                // alert(response);
                console.log(response);
                setWishlistItems((prev) => (
                    [...prev,
                        productId
                    ]
                ));
            }
            catch (err) {
                console.log(err);
            }
        }
        else {
            try {
                const response = await removeFromWishlist(wishlistData);
                // alert(response);
                console.log(response);
                setWishlistItems((prev) =>
                    prev.filter((id) => id !== productId)
                );
            }
            catch (err) {
                console.log(err);
            }
        }
    }

    const handlePlaceOrder = async () => {
        try {
            const productId = Number(product?.productId);
            const response = await placeDirectOrder(productId);
            alert(response);
            window.location.href = "/products";
        }
        catch (err) {
            alert(err);
        }
    }

    if (!product) {
        return (
            <div className="text-center mt-5">
                <h3>Loading...</h3>
            </div>
        );
    }

    const discountedPrice =
        product.price -
        (product.price * product.discountPercentage) / 100;

    return (

        <div className="container py-5">

            <div className="row shadow-lg rounded-4 bg-white p-4">

                {/* Product Image */}

                <div className="col-md-6 text-center">

                    <img
                        src={product.imageUrl}
                        alt={product.productName}
                        className="img-fluid rounded-4"
                        style={{
                            maxHeight: "500px",
                            objectFit: "contain"
                        }}
                    />

                </div>

                {/* Product Info */}



                <div className="col-md-6 d-flex flex-column justify-content-center position-relative">

                    <div className="position-absolute top-0 end-0 p-2">
                        <button className="btn"
                            onClick={() => {
                                handleWishlist(product.productId);
                            }}>
                            {
                                wishlistItems.includes(product.productId)
                                    ? "❤️" : "🤍"
                            }
                        </button>
                    </div>

                    <h1 className="fw-bold">
                        {product.productName}
                    </h1>

                    <h5 className="text-secondary mt-2">
                        Brand: {product.brand}
                    </h5>

                    <p className="mt-4 fs-5 text-muted">
                        {product.description}
                    </p>

                    <h5 className="text-warning mt-2">
                        ⭐ {product.rating}
                    </h5>

                    <div className="d-flex align-items-center gap-3 mt-4">

                        <h2 className="text-success fw-bold m-0">
                            ₹ {discountedPrice}
                        </h2>

                        <h5 className="text-decoration-line-through text-secondary m-0">
                            ₹ {product.price}
                        </h5>

                        <h5 className="text-danger fw-bold m-0">
                            {product.discountPercentage}% OFF
                        </h5>

                    </div>

                    <div className="d-flex gap-3 mt-4">

                        {/* <button className="btn btn-warning btn-lg px-4">
                            Add To Cart
                        </button> */}

                        <button className={`btn mt-auto rounded-3 ${addedItems.includes(product.productId)
                            ? "btn-success"
                            : "btn-warning"
                            }`}
                            onClick={() => handleAddToCart(product.productId)}
                            disabled={addedItems.includes(product.productId)} >
                            {
                                addedItems.includes(product.productId)
                                    ? "Added"
                                    : "Add to cart"
                            }
                        </button>

                        <button className="btn btn-dark mt-auto rounded-3"
                            onClick={handlePlaceOrder} >
                            Buy Now
                        </button>

                    </div>

                </div>

            </div>

        </div>
    );
};

export default ProductDetails;

