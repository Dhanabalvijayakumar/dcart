// import axios from "axios";
import API from "./AxiosConfig";

// const API = "http://localhost:8081/api/auth/user";

const USER_API = "/auth/user";

export const checkUser = async (data: any) => {
    // const res = await axios.post(`${API}/login`, data);
    const res = await API.post(`${USER_API}/login`, data);
    return res.data;
}

export const addUser = async (data: any) => {
    // const res = await axios.post(`${API}/register`, data);
    const res = await API.post(`${USER_API}/register`, data);
    return res.data;
}

export const checkEmail = async (email: string) => {
    // const res = await axios.get(`${API}/checkEmail?email=${email}`);
    const res = await API.get(`${USER_API}/checkEmail?email=${email}`);
    return res.data;
}

export const logOut = async () => {
    const token = localStorage.getItem("token");

    // const res = await axios.post(`${API}/logout`,
    const res = await API.post(`${USER_API}/logout`,
        {},
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );
    return res.data;
}