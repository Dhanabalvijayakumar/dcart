import { useEffect, useState } from "react";
import { getWishlist, removeFromWishlist } from "../api/WishlistAPI";
import { addToCart } from "../api/CartProdAPI";
import { Link } from "react-router-dom";
import { Product } from "../model/Product";

const Wishlist = () => {

    const [wishlist, setWishlist] = useState<Product[]>([]);

    const [addedItems, setAddedItems] = useState<number[]>([]);

    useEffect(() => {
        fetchWishlist();
    }, []);

    const fetchWishlist = async () => {
        try {
            const response = await getWishlist();
            console.log(response);
            setWishlist(response);
        }
        catch (err) {
            console.log(err);
        }
    }

    const handleWishlist = async (productId: number) => {
        const userId = localStorage.getItem("userId");
        const wishlistData = {
            "userId": Number(userId),
            "productId": productId
        };

        try {
            const response = await removeFromWishlist(wishlistData);
            // alert(response);
            console.log(response);
            // await fetchWishlist();
            setWishlist((prev) => (
                prev.filter((item) =>
                    item.productId !== productId)
            ));
        }
        catch (err) {
            console.log(err);
        }
    }

    const handleAddToCart = async (productId: number) => {
        try {
            const userId = localStorage.getItem("userId");
            // console.log(userId);
            const cartData = {
                "userId": Number(userId),
                "productId": productId
            };
            const response = await addToCart(cartData);
            console.log(response);
            // alert(response);
            setAddedItems((prev) => [...prev, productId])

            setTimeout(() => {
                setAddedItems([]);
            }, 3000);
        }
        catch (err) {
            console.log(err)
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

            <div className="container py-5 mt-3 mb-3 m-auto">
                {
                    wishlist.length === 0 ? (
                        <div className="d-flex flex-column align-items-center justify-content-center"
                            style={{ minHeight: "70vh" }}>
                            <h3 className="fw-bold text-dark">Your wishlist is empty ❤️</h3>
                            <p className="text-muted fs-5 mt-2">Add products to wishlist</p>
                            <Link to="/products"
                                className="btn btn-primary rounded-pill px-4" >Continue shopping</Link>
                        </div>
                    ) : (

                        <div>
                            <div className="mb-4 text-center">
                                <h2 className="fw-bold">
                                    My Wishlist ❤️
                                </h2>
                                <p className="text-muted">
                                    Your favourite saved products
                                </p>
                            </div>
                            <div className="row">
                                {
                                    wishlist.map((wishProd) => (
                                        <div className="col-sm-6 col-md-4 col-lg-3 mb-4"
                                            key={wishProd.productId} >
                                            <div className="card product-card shadow-lg border-0 bg-light rounded-4 m-2 h-100 overflow-hidden">
                                                <div className="bg-white d-flex justify-content-center align-items-center"
                                                    style={{
                                                        height: "250px",
                                                        overflow: "hidden",
                                                        padding: "15px"
                                                    }}>
                                                    <img src={wishProd.imageUrl}
                                                        alt={wishProd.productName}
                                                        className="card-img-top"
                                                        style={{
                                                            objectFit: "cover",
                                                            maxHeight: "100%",
                                                            maxWidth: "100%",
                                                            transition: "transform 0.3s ease"
                                                        }} />
                                                </div>
                                                {/* <div className="d-flex justify-content-end"> */}
                                                <div className="position-absolute top-0 end-0 p-2">
                                                    <button className="btn"
                                                        onClick={() => {
                                                            handleWishlist(wishProd.productId);
                                                        }}>
                                                        ❤️
                                                    </button>
                                                </div>
                                                <div className="card-body d-flex flex-column">
                                                    <h5 className="fw-bold">
                                                        {wishProd.productName}
                                                    </h5>
                                                    <p className="mb-1 text-muted small">
                                                        Brand: <span className="fw-semibold">{wishProd.brand}</span>
                                                    </p>
                                                    <p className="mb-2 small text-warning">
                                                        ⭐ {wishProd.rating}
                                                    </p>
                                                    <p className="text-muted small">
                                                        {wishProd.description}
                                                    </p>
                                                    <h5 className="text-success fw-bold">
                                                        ₹  {
                                                            Math.round(
                                                                wishProd.price -
                                                                (wishProd.price * wishProd.discountPercentage) / 100
                                                            )
                                                        }
                                                    </h5>
                                                    {/* <button className="btn btn-warning mt-auto rounded-3"
                                                        onClick={() => handleAddToCart(wishProd.productId)} >
                                                        Add to cart
                                                    </button> */}
                                                    <button className={`btn mt-auto rounded-3 ${addedItems.includes(wishProd.productId)
                                                        ? "btn-success"
                                                        : "btn-warning"
                                                        }`}
                                                        onClick={() => handleAddToCart(wishProd.productId)}
                                                        disabled={addedItems.includes(wishProd.productId)} >
                                                        {
                                                            addedItems.includes(wishProd.productId)
                                                                ? "Added"
                                                                : "Add to cart"
                                                        }
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    ))
                                }
                            </div>
                        </div>
                    )
                }
            </div>
        </>
    )
}

export default Wishlist;