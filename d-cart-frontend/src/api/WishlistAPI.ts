import API from "./AxiosConfig";

// const API = "http://localhost:8081/api/wishlist";
const WISHLIST_API = "/wishlist";

export const addToWishlist = async (data: any) => {
    const token = localStorage.getItem("token");

    // const res = await axios.post(`${API}/add`, data,
    const res = await API.post(`${WISHLIST_API}/add`, data,

        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const removeFromWishlist = async (data: any) => {
    const token = localStorage.getItem("token");

    // const res = await axios.delete(`${API}/delete`,
    const res = await API.delete(`${WISHLIST_API}/delete`,
        {
            data: data,
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const getWishlist = async () => {
    const token = localStorage.getItem("token");

    // const res = await axios.get(`${API}`,
    const res = await API.get(`${WISHLIST_API}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}