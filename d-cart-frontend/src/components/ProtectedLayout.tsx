import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Footer from "./Footer";
import { useEffect, useState } from "react";

const ProtectedLayout = () => {

    // const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (!token) {
            // navigate("/");
            window.location.href = "/";
        }
    }, []);

    return (
        <>
            <div className="d-flex flex-column min-vh-100">
                <Navbar
                    searchTerm={searchTerm}
                    setSearchTerm={setSearchTerm} />
                <div className="flex-grow-1">
                    <Outlet context={{ searchTerm }} />
                </div>
                <Footer />
            </div>
        </>
    )
}

export default ProtectedLayout;