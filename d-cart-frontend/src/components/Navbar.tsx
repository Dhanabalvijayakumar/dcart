import { Link } from "react-router-dom";
import logo from "../assets/D'Cart Logo White Theme.png";
import { logOut } from "../api/UserAPI";

type NavbarProps = {
    searchTerm: string;
    setSearchTerm: React.Dispatch<React.SetStateAction<string>>;
};

const Navbar = ({
    searchTerm,
    setSearchTerm
}: NavbarProps) => {

    // const navigate = useNavigate();

    // const [searchTerm, setSearchTerm] = useState("");

    const handleLogout = async () => {
        try {
            const response = await logOut();
            localStorage.clear();
            alert(response);
            // navigate("/");
            window.location.href = "/";
        }
        catch (err) {
            console.log("err");
        }
    }

    return (
        <>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm sticky-top">
                <div className="container-fluid">
                    <Link className="navbar-brand fw-bold fs-3" to="/products">
                        <img src={logo} alt="D'Cart Logo"
                            width="70"
                            className="me-2 rounded-circle" />
                    </Link>
                    <div className="d-flex justify-content-center px-4 gap-4">
                        <div className="d-flex" style={{ width: "250px" }}>
                            <input type="text" name="search" id="search"
                                className="form-control"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                placeholder="Search products..." />
                            <button className="btn ">
                                <i className="bi bi-search"></i>
                                🔍
                            </button>
                        </div>

                        <Link to="/wishlist" title="My Wishlist" className="text-white text-decoration-none fs-4">
                            <i className="bi bi-heart"></i>
                            ❤️
                        </Link>
                        <Link to="/cart" title="My Cart" className="text-white text-decoration-none fs-4">
                            <i className="bi bi-cart3"></i>
                            🛒
                        </Link>
                        <Link to="/orders" title="My Orders" className="text-white text-decoration-none fs-4">
                            <i className="bi bi-cart3"></i>
                            📦
                        </Link>
                        <button className="btn text-white fs-4 border-0"
                            data-bs-toggle="dropdown" >
                            <i className="bi bi-person-circle"></i>
                            👤
                        </button>
                        <ul className="dropdown-menu dropdown-menu-end">
                            <li>
                                <button className="dropdown-item" onClick={handleLogout}>
                                    Logout
                                </button>
                            </li>
                        </ul>

                        {/* <Link to="" className="text-white text-decoration-none fs-4">
                            <i className="bi bi-person-circle"></i>
                            👤
                        </Link> */}
                    </div>
                </div>
            </nav>
        </>
    )
}

export default Navbar;