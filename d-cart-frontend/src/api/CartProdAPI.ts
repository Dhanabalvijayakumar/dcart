import API from "./AxiosConfig";

// const API = "http://localhost:8081/api/cart";
const CART_API = "/cart";

export const addToCart = async (data: any) => {
    const token = localStorage.getItem("token");

    // const res = await axios.post(`${API}/add`, data,
    const res = await API.post(`${CART_API}/add`, data,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const removeFromCart = async (data: any) => {
    const token = localStorage.getItem("token");

    // const res = await axios.delete(`${API}/delete`,
    const res = await API.delete(`${CART_API}/delete`,
        {
            data: data,
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const getCartItems = async () => {
    const token = localStorage.getItem("token");

    // const res = await axios.get(`${API}`,
    const res = await API.get(`${CART_API}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const placeOrder = async () => {
    const token = localStorage.getItem("token");

    // const res = await axios.post(`${API}/placeOrder`,
    const res = await API.post(`${CART_API}/placeOrder`,
        {},
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const placeDirectOrder = async (productId: number) => {
    const token = localStorage.getItem("token");

    // const res = await axios.post(`${API}/placeOrder`,
    const res = await API.post(`${CART_API}/placeDirectOrder/${productId}`,
        {},
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}