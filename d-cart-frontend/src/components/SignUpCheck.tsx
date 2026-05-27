import { useState } from "react";
import logo from "../assets/D'Cart Logo White Theme.png";
import { checkEmail } from "../api/UserAPI";
import { useNavigate } from "react-router-dom";

const SignUpCheck = () => {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [check, setCheck] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await checkEmail(email);
            if (response) {
                navigate("/signupLanding", {
                    state: { email }
                });
                // window.location.href = "/signupLanding";
            }
        }
        catch (err) {
            alert("Email already registered with D'Cart");
            // navigate("/login");
            window.location.href = "/login";
        }
    }

    return (
        <>

            <style>
                {`
                    body {
                        background-color: #f5f7fb;
                    }
                    
                `}
            </style>

            <div className="container justify-content-center align-items-center px-5 py-5">
                <div className="card shadow-sm border-0 rounded-4 p-4">
                    <div className="card-body">
                        <div className="text-center">
                            <img src={logo} alt="D'Cart Logo"
                                className="img-fluid"
                                style={{
                                    width: "20%",
                                    height: "auto",
                                    objectFit: "contain"
                                }} />
                            <h2 className="fw-bold mb-2">Welcome to D'Cart</h2>
                            <p className="text-muted">Sign Up or create your account</p>
                        </div>
                        <div className="">
                            <form action="" onSubmit={handleSubmit}>
                                <div className="form-group">
                                    <label htmlFor="email" className="form-label fw-bold">Email Address </label>
                                    <input type="email" name="email" id="email"
                                        className="form-control form-control-lg"
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                        required />
                                </div>
                                <div className="text-center">
                                    {check &&
                                        <button className="btn btn-primary m-3"
                                            type="submit" >
                                            Continue
                                        </button>
                                    }
                                    {!check &&
                                        <button className="btn btn-primary m-3 disabled"
                                            type="submit" >
                                            Continue
                                        </button>
                                    }
                                </div>
                                <div className="form-check mt-1">
                                    <input type="checkbox" name="agreement" id="agreement"
                                        className="form-check-input"
                                        onChange={(e) => setCheck(!check)} />
                                    <label htmlFor="agreement"
                                        className="form-check-label text-muted" >
                                        By continuing, you agree to D'Cart's Term of Service and Privacy Policy.
                                    </label>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </>
    )

}

export default SignUpCheck;