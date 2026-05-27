// import { useNavigate } from "react-router-dom";
import { useLocation, useNavigate } from "react-router-dom";
import logo from "../assets/D'Cart Logo White Theme.png";

const SignUpLanding = () => {

    const navigate = useNavigate();

    const location = useLocation();
    const email = location.state?.email;

    return (
        <>

            <style>
                {`
                    body {
                        background-color: #f5f7fb;
                    }
                    
                `}
            </style>

            <div className="container px-4 py-5 d-flex align-items-center justify-content-center">
                <div className="card shadow-sm rounded-4 border-0 p-4"
                    style={{
                        maxWidth: "70%"
                    }}>
                    <div className="card-body text-center">
                        <img src={logo} alt="D'Cart Logo"
                            style={{
                                width: "20%",
                                height: "auto",
                                objectFit: "contain"
                            }} />
                        <h3 className="text-center fw-semibold">Looks like you are new to D'Cart</h3>
                        <p>Let's create a account for you with D'Cart</p>
                        <button className="btn btn-primary"
                            type="submit"
                            onClick={() => navigate("/signup", {
                                state: { email }
                            })}
                        // onClick={() => window.location.href = "/signup"}
                        >
                            Proceed to continue
                        </button>
                    </div>
                </div>
            </div>
        </>
    )

}

export default SignUpLanding;