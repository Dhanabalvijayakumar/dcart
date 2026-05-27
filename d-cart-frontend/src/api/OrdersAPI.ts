import API from "./AxiosConfig";

// const API = "http://localhost:8081/api/orders";
const ORDERS_API = "/orders";

export const loadOrders = async () => {
    const token = localStorage.getItem("token");

    // const res = await axios.get(`${API}`,
    const res = await API.get(`${ORDERS_API}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}