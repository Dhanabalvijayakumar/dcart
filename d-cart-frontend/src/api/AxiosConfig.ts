import axios from "axios";

const API = axios.create({
    // baseURL: "http://localhost:8081/api"
    baseURL: "https://dcart-backend.onrender.com/"
});

API.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && 
            (error.response.status === 401 || error.response.status === 403)) {
            localStorage.removeItem("token");
            localStorage.removeItem("userId");

            alert("Session expired. Please login again.");

            window.location.href = "/";
        }
        return Promise.reject(error);
    }
);

export default API;