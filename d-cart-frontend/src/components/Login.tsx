import { useState } from "react";
import { Link } from "react-router-dom";
import { checkUser } from "../api/UserAPI";
import logo from "../assets/D'Cart Logo White Theme.png"

const Login = () => {

    // const navigate = useNavigate();

    const [emailOrMobile, setEmailOrMobile] = useState("");
    const [password, setPassword] = useState("");

    const [errorMsg, setErrorMsg] = useState("");

    const convertToBackendLogin = () => {
        const isMobile = /^[0-9]+$/.test(emailOrMobile);
        if (isMobile) {
            return {
                mobile: emailOrMobile,
                password: password
            };
        }

        return {
            email: emailOrMobile,
            password: password
        }
    }

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();

        setErrorMsg("");

        try {

            const loginData = convertToBackendLogin();
            const response = await checkUser(loginData);
            console.log(response);

            const { token, userId, name, email, mobile, password, role, createdTime } = response;

            localStorage.setItem("token", token);
            localStorage.setItem("userId", userId);
            localStorage.setItem("name", name);
            localStorage.setItem("email", email);
            localStorage.setItem("mobile", mobile);
            localStorage.setItem("password", password);
            localStorage.setItem("role", role);
            localStorage.setItem("createdTime", createdTime);

            setEmailOrMobile("");
            setPassword("");

            console.log({
                emailOrMobile, password
            });

            // alert("Login successful");
            console.log("Login successful");
            // navigate("/products");
            window.location.href = "/products";

        }
        catch (err: any) {
            setErrorMsg("Invalid Credentials");
            // alert("Invalid credentials");
        }
    }

    return (
        <>

            <style>
                {`
                    body {
                        background-color: #f5f7fb;
                    }
                    .btn-success: hover {
                        transform: translateY(-10px);
                        transition: 0.3s;
                    }
                `}
            </style>

            <div className="container px-5 py-5 justify-content-center align-items-center login" style={{ maxWidth: "80%" }}>
                {/* <div className="title text-center fw-bold logo">D'Cart</div> */}
                <div className="card shadow-lg border-0 rounded-4 p-4">
                    <div className="card-body login-box">

                        <div className="text-center mb-4">
                            {/* <div className="mb-3">
                                <FaShoppingCart size={45} className="text-primary"></FaShoppingCart>
                            </div> */}
                            {/* <h2 className="fw-bold logo">D'Cart</h2> */}
                            <div className="text-center">
                                <img src={logo} alt="D'Cart Logo" className="img-fluid"
                                    style={{
                                        width: "20%",
                                        height: "auto",
                                        objectFit: "contain"
                                    }} />
                            </div>
                            <p className="text-muted mb-0">Welcome back! Please sign in</p>
                        </div>

                        {/* <h2 className="title text-center fw-bold">Sign In</h2> */}

                        <form action="" onSubmit={handleLogin}>
                            <div className="form-group">
                                <label htmlFor="email" className="form-label fw-bold">Email / Mobile </label>
                                <input type="text" name="email" id="email"
                                    className="form-control"
                                    placeholder="Enter your Email or Mobile number"
                                    value={emailOrMobile}
                                    onChange={(e) => setEmailOrMobile(e.target.value)}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password" className="form-label fw-bold">Password </label>
                                <input type="password" name="password" id="password"
                                    className="form-control"
                                    placeholder="Enter your password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required />
                            </div>

                            <div className="d-grid">
                                <button className="btn btn-success px-5 my-3 fw-semibold"
                                    type="submit" >
                                    SignIn
                                </button>
                            </div>

                            {errorMsg && (
                                <div className="text-danger text-center mt-2">
                                    {errorMsg}
                                </div>
                            )}

                        </form>

                        <div className="text-center mt-4 new-user">
                            <span className="text-muted">New customer?</span>
                            <Link to="/signupCheck" className="text-decoration-none fw-bold ms-2">Start here</Link>
                        </div>

                    </div>
                </div>
            </div>
        </>
    )

}

export default Login;