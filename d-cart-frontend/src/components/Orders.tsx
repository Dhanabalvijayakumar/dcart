import { useEffect, useState } from "react";
import { loadOrders } from "../api/OrdersAPI";
import { Link } from "react-router-dom";
import { OrderItems } from "../model/OrderItems";

const Orders = () => {

    const [orderItems, setOrderItems] = useState<OrderItems[]>([]);

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            const response = await loadOrders();
            console.log(response);
            setOrderItems(response);
        }
        catch (err) {
            console.log(err);
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
                    orderItems.length === 0 ? (
                        <div className="d-flex flex-column justify-content-center align-items-center"
                            style={{ minHeight: "70vh" }} >
                            <h2 className="fw-bold">You haven't placed any orders yet 📦</h2>
                            <p className="text-muted fs-5">Your order history will appear here once you place an order</p>
                            <Link to="/products"
                                className="btn btn-primary rounded-pill px-4" >
                                Continue shopping
                            </Link>
                        </div>
                    ) : (
                        <div className="container mt-4">
                            <div className="mb-5 text-center">
                                <h2 className="fw-bold">Order History</h2>
                            </div>

                            {
                                orderItems.map((order) => (
                                    <div
                                        key={order.orderItemId}
                                        className="card border-0 shadow-sm rounded-4 mb-4 p-3" >

                                        <div className="row align-items-center">
                                            <div className="col-md-2 text-center">

                                                <img
                                                    src={order.productsDTO.imageUrl}
                                                    alt={order.productsDTO.productName}
                                                    className="img-fluid rounded-3"
                                                    style={{
                                                        objectFit: "contain"
                                                    }}
                                                />

                                            </div>
                                            <div className="col-md-6">

                                                <h4 className="fw-bold mb-2">
                                                    {order.productsDTO.productName}
                                                </h4>
                                                <p className="text-muted mb-2">
                                                    {order.productsDTO.description}
                                                </p>
                                                <div className="mb-1">
                                                    <span className="fw-semibold">
                                                        Quantity:
                                                    </span>
                                                    <span className="ms-2">
                                                        {order.quantity}
                                                    </span>
                                                </div>
                                                <div className="mb-1">
                                                    <span className="fw-semibold">
                                                        Item Price:
                                                    </span>
                                                    <span className="ms-2 text-success fw-bold">
                                                        ₹{
                                                            (
                                                                order.price /
                                                                order.quantity
                                                            ).toFixed(2)
                                                        }
                                                    </span>
                                                </div>
                                                <div>
                                                    <span className="fw-semibold">
                                                        Total Amount:
                                                    </span>
                                                    <span className="ms-2 fw-bold">
                                                        ₹{order.price}
                                                    </span>
                                                </div>
                                            </div>
                                            <div className="col-md-4 text-md-end mt-3 mt-md-0">
                                                <div className="mb-3">
                                                    <span className="badge bg-success px-3 py-2 fs-6">
                                                        {order.ordersDTO.status}
                                                    </span>
                                                </div>
                                                <div className="text-muted small">
                                                    Order ID:
                                                    #{order.ordersDTO.orderId}
                                                </div>
                                                <div className="text-muted small mt-1">
                                                    Ordered On:
                                                    {
                                                        new Date(
                                                            order.ordersDTO.createdTime.replace(" ", "T")
                                                        ).toLocaleDateString("en-IN", {
                                                            day: "numeric",
                                                            month: "short",
                                                            year: "numeric"
                                                        })
                                                    }
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            }
                        </div>
                    )
                }
            </div>
        </>
    )
}

export default Orders;