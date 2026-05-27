import API from "./AxiosConfig";

// const API = "http://localhost:8081/api/product";
const PRODUCT_API = "/product";

const token = localStorage.getItem("token");

export const loadProducts = async (page: number) => {
    // const res = await axios.get(`${API}/loadProducts`,
    const res = await API.get(`${PRODUCT_API}/loadProducts?page=${page}&size=12`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}

export const getProductById = async (productId: number) => {
    const res = await API.get(
        `${PRODUCT_API}/${productId}`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}