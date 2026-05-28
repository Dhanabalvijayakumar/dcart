import { useEffect, useState } from "react";
import { loadProducts } from "../api/ProductsAPI";
import { addToCart } from "../api/CartProdAPI";
import { addToWishlist, getWishlist, removeFromWishlist } from "../api/WishlistAPI";
import { useNavigate, useOutletContext } from "react-router-dom";
import { Product } from "../model/Product";

const Products = () => {

    const navigate = useNavigate();

    const { searchTerm }: any = useOutletContext();

    const [products, setProducts] = useState<Product[]>([]);

    const [wishlistItems, setWishlistItems] = useState<number[]>([]);

    const [addedItems, setAddedItems] = useState<number[]>([]);

    const token = localStorage.getItem("token");

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);


    const [loading, setLoading] = useState(true);
    const [backendError, setBackendError] = useState(false);




    const fetchWishlist = async () => {
        try {
            const response = await getWishlist();

            const wishlistProductIds = response.map(
                (item: Product) => item.productId
            );
            setWishlistItems(wishlistProductIds);
        }
        catch (err) {
            console.log(err);
            setWishlistItems([]);
        }
    }

    useEffect(() => {

        const fetchProducts = async () => {
            try {
                setLoading(true);
                setBackendError(false);

                const response = await loadProducts(page);
                // console.log(response);
                setProducts(response.content);
                setTotalPages(response.totalPages);

                if (token) {
                    fetchWishlist();
                }

            }
            catch (err) {
                console.log(err);
                setBackendError(true);
            }
            finally {
                setLoading(false);
            }
        };

        fetchProducts();

    }, [page, token]);


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

    const filteredProducts = products.filter((product) => {
        const search = searchTerm.toLowerCase();

        return (
            product.productName.toLowerCase().includes(search) ||
            product.brand.toLowerCase().includes(search) ||
            product.description.toLowerCase().includes(search) ||
            product.categoriesDTO?.name?.toLowerCase().includes(search)
        );
    });


    if (loading) {
        return (
            <div className="d-flex justify-content-center align-items-center vh-100">
                <h3>Loading...</h3>
            </div>
        );
    }

    if (backendError) {
        return (
            <div className="d-flex justify-content-center align-items-center vh-100">
                <div className="text-center">
                    <h2 className="text-danger">Backend Server Not Responding</h2>
                    <p>Please try again later</p>
                </div>
            </div>
        );
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

            <div className="container text-center mt-4">
                <h1 className="fw-bold display-5">Discover Amazing Products</h1>
                <p className="text-muted fs-5 mt-3">
                    Shop electronics, fashion, books and more
                </p>
            </div>

            <div className="container row m-auto">
                {
                    filteredProducts.map((product) => (
                        <div className="col-sm-6 col-md-4 col-lg-3 mb-4"
                            key={product.productId} >
                            <div className="card product-card shadow-sm border-0 rounded-4 m-2 h-100 overflow-hidden ">
                                <div className="bg-white d-flex justify-content-center align-items-center"
                                    style={{
                                        height: "250px",
                                        overflow: "hidden",
                                        padding: "15px"
                                    }}>
                                    <img src={product.imageUrl}
                                        alt={product.productName}
                                        className="card-img-top"
                                        style={{
                                            objectFit: "cover",
                                            maxHeight: "100%",
                                            maxWidth: "100%",
                                            transition: "transform 0.3s ease"
                                        }}
                                        onClick={() => navigate(`/product/${product.productId}`)} />
                                </div>
                                {/* <div className="d-flex justify-content-end"> */}
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
                                <div className="card-body d-flex flex-column">
                                    <h5 className="fw-bold">
                                        {product.productName}
                                    </h5>
                                    <p className="mb-1 text-muted small">
                                        Brand: <span className="fw-semibold">{product.brand}</span>
                                    </p>
                                    <p className="mb-2 small text-warning">
                                        ⭐ {product.rating}
                                    </p>
                                    <p className="text-muted small">
                                        {product.description}
                                    </p>
                                    {/* <h5 className="text-success fw-bold">
                                        ₹ {product.price}
                                    </h5> */}
                                    <div className="d-flex align-items-center gap-2 mb-2">
                                        <h5 className="text-success fw-bold m-0">
                                            ₹ {Math.round(
                                                product.price -
                                                (product.price * product.discountPercentage) / 100
                                            )}
                                        </h5>
                                        <small className="text-muted text-decoration-line-through">
                                            ₹ {product.price}
                                        </small>
                                        <small className="text-danger fw-bold">
                                            {product.discountPercentage}% OFF
                                        </small>
                                    </div>
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
                                </div>
                            </div>
                        </div>
                    ))
                }
            </div>
            <div className="d-flex justify-content-center align-items-center my-4 gap-2 flex-wrap">
                <button disabled={page === 0}
                    onClick={() => setPage(page - 1)}
                    className="btn btn-dark" >
                    Previous
                </button>
                {[...Array(totalPages)].map((_, index) => (
                    <button
                        key={index}
                        onClick={() => setPage(index)}
                        className={`btn ${page === index ?
                            "btn-warning text-dark fw-bold" : "btn-outline-dark"}`} >
                        {index + 1}
                    </button>
                ))
                }
                <button disabled={page === totalPages - 1}
                    onClick={() => setPage(page + 1)}
                    className="btn btn-dark" >
                    Next
                </button>
            </div>
        </>
    )
}

export default Products;