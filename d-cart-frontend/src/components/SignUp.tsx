import React, { useState } from "react";
import logo from "../assets/D'Cart Logo White Theme.png";
import { addUser } from "../api/UserAPI";
import { Link, useLocation } from "react-router-dom";
import { UserData } from "../model/UserData";

const SignUp = () => {

    // const navigate = useNavigate();

    const location = useLocation();
    const userEmail = location.state?.email || "";

    const [form, setForm] = useState<UserData>({
        name: "",
        email: userEmail || "",
        mobile: "",
        password: "",
        role: ""
    });

    const [rePaswword, setRePassword] = useState("");

    const [successMsg, setSuccessMsg] = useState("");
    const [errors, setErrors] = useState<any>({});

    const [loading, setLoading] = useState(false);

    const validateField = (name: string, value: string) => {
        let error = "";

        switch (name) {

            case "name":
                if (!value.trim()) {
                    error = "Full name is required";
                }
                else if (value.length < 3) {
                    error = "Name must be at least 3 characters";
                }
                else if (!/^[A-Z][a-z]+( [A-Z][a-z]+)+$/.test(value)) {
                    error = "Invalid name format"
                }
                break;

            case "email":
                if (!value.trim()) {
                    error = "Email is required";
                }
                else if (!/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/.test(value)) {
                    error = "Invalid email address";
                }
                break;

            case "mobile":
                if (!value.trim()) {
                    error = "Mobile number is required";
                }
                else if (!/^[0-9]{10}$/.test(value)) {
                    error = "Mobile number must contain 10 digits";
                }
                break;

            case "password":
                if (!value.trim()) {
                    error = "Password is required";
                }
                else if (value.length < 6) {
                    error = "Password must be at least 6 characters";
                }
                break;

            case "role":
                if (!value) {
                    error = "Please select a role";
                }
                break;

        }

        setErrors((prev: any) => ({
            ...prev,
            [name]: error
        }));

    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: value
        }));

        validateField(name, value);
    }

    const validateRePass = (name: string, value: string) => {
        let rePassError = "";
        switch (name) {
            case "rePassword":
                if (!value.trim()) {
                    rePassError = "Confirm your password";
                }
                else if (value !== form.password) {
                    rePassError = "Password doesn't match";
                }
                break;
        }
        setErrors((prev: any) => ({
            ...prev,
            [name]: rePassError
        }));
    }

    const handleRePassChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setRePassword(value);

        validateRePass(name, value);
    }

    const validateForm = () => {
        let newErrors: any = {};

        if (!form.name.trim()) {
            newErrors.name = "Full name is required";
        }
        else if (form.name.length < 3) {
            newErrors.name = "Name must be at least 3 characters";
        }
        else if (!/^[A-Z][a-z]+( [A-Z][a-z]+)+$/.test(form.name)) {
            newErrors.name = "Invalid name format"
        }

        if (!form.email.trim()) {
            newErrors.email = "Email is required";
        }
        else if (!/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/.test(form.email)) {
            newErrors.email = "Invalid email address";
        }

        if (!form.mobile.trim()) {
            newErrors.mobile = "Mobile number is required";
        }
        else if (!/^[0-9]{10}$/.test(form.mobile)) {
            newErrors.mobile = "Mobile number must contain 10 digits";
        }

        if (!form.password.trim()) {
            newErrors.password = "Password is required";
        }
        else if (form.password.length < 6) {
            newErrors.password = "Password must be at least 6 characters";
        }

        if (!rePaswword.trim()) {
            newErrors.rePassword = "Confirm your password";
        }
        else if (rePaswword !== form.password) {
            newErrors.rePassword = "Password doesn't match";
        }

        if (!form.role) {
            newErrors.role = "Please select a role";
        }

        setErrors(newErrors);

        return Object.keys(newErrors).length === 0;

    }

    const checkFormValid = () => {
        return (
            form.name.trim() !== "" &&
            /^[A-Z][a-z]+( [A-Z][a-z]+)+$/.test(form.name) &&

            form.email.trim() !== "" &&
            /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/.test(form.email) &&

            /^[0-9]{10}$/.test(form.mobile) &&

            form.password.length >= 6 &&

            rePaswword.trim() !== "" &&
            rePaswword === form.password &&

            form.role !== ""
        );
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setSuccessMsg("");

        if (!validateForm()) {
            return;
        }
        setLoading(true);
        setErrors({});
        try {
            const response = await addUser(form);
            console.log(response);
            setForm({
                name: "",
                email: "",
                mobile: "",
                password: "",
                role: ""
            });
            setSuccessMsg("Account created successfully");
            setTimeout(() => {
                // navigate("/login");
                window.location.href = "/login";
            }, 2000);
        }
        catch (err) {
            console.log(err);
            alert("User not added!");
        }
        finally {
            setLoading(false);
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

            <div className="container px-5 py-5 justify-content-center align-items-center" style={{ maxWidth: "80%" }}>
                <div className="card shadow-lg border-0 rounded-4 p-4" >
                    <div className="card-body register-box">

                        <div className="text-center mb-4">
                            <img src={logo} alt="D'Cart Logo"
                                className="img-fluid"
                                style={{
                                    width: "20%",
                                    height: "auto",
                                    objectFit: "contain"
                                }} />
                            <h3 className="fw-bold mb-2">Create Account</h3>
                            <p className="text-muted small mb-0">Join D'Cart and start shopping smarter</p>
                        </div>

                        <div className="container px-5">
                            <form action="" onSubmit={handleSubmit}>

                                <div className="form-group">
                                    <label htmlFor="name" className="form-label fw-bold">Full Name </label>
                                    <input type="text" name="name" id="name"
                                        className="form-control"
                                        placeholder="Enter your fullname"
                                        value={form.name}
                                        onChange={handleChange}
                                        required />
                                </div>
                                {
                                    errors.name &&
                                    <small className="text-danger">{errors.name}</small>
                                }
                                <div className="form-group">
                                    <label htmlFor="email" className="form-label fw-bold">Email ID </label>
                                    <input type="email" name="email" id="email"
                                        className="form-control"
                                        placeholder="Enter your email"
                                        value={form.email}
                                        onChange={handleChange}
                                        required
                                        disabled />
                                </div>
                                {
                                    errors.email &&
                                    <small className="text-danger">{errors.email}</small>
                                }
                                <div className="form-group">
                                    <label htmlFor="mobile" className="form-label fw-bold">Mobile </label>
                                    <input type="number" name="mobile" id="mobile"
                                        className="form-control"
                                        placeholder="Enter your mobile number"
                                        value={form.mobile}
                                        onChange={handleChange}
                                        required />
                                    {
                                        errors.mobile &&
                                        <small className="text-danger">{errors.mobile}</small>
                                    }
                                </div>
                                <div className="form-group">
                                    <label htmlFor="password" className="form-label fw-bold">Password </label>
                                    <input type="password" name="password" id="password"
                                        className="form-control"
                                        placeholder="Enter your password"
                                        value={form.password}
                                        onChange={handleChange}
                                        required />
                                    {
                                        errors.password &&
                                        <small className="text-danger">{errors.password}</small>
                                    }
                                </div>
                                <div className="form-group">
                                    <label htmlFor="rePassword" className="form-label fw-bold">Re-enter password </label>
                                    <input type="password" name="rePassword" id="rePassword"
                                        className="form-control"
                                        placeholder="Re-enter your password"
                                        value={rePaswword}
                                        onChange={handleRePassChange}
                                        required />
                                    {
                                        errors.rePassword &&
                                        <small className="text-danger">{errors.rePassword}</small>
                                    }
                                </div>
                                <div className="form-group">
                                    <label htmlFor="role" className="form-label fw-bold">Role </label>
                                    <select name="role" id="role" className="form-select" value={form.role} onChange={handleChange}>
                                        <option value="" disabled>--SELECT YOUR ROLE--</option>
                                        <option value="USER">USER</option>
                                        <option value="ADMIN" disabled>ADMIN</option>
                                    </select>
                                    {
                                        errors.role &&
                                        <small className="text-danger">{errors.role}</small>
                                    }
                                </div>

                                <div className="d-grid">
                                    <button className={`btn px-5 my-3 fw-semibold ${checkFormValid() && !loading
                                            ? "btn-success" : "btn-secondary"
                                        }`}
                                        type="submit"
                                        disabled={!checkFormValid()} >
                                        {
                                            loading
                                                ? "Creating Account..."
                                                : "Create Account"
                                        }
                                    </button>
                                </div>

                                {successMsg && (
                                    <div className="text-success text-center mt-2">
                                        {successMsg}
                                    </div>
                                )}

                            </form>

                            <div className="text-center mt-4">
                                <span className="text-muted">Already have an account?</span>
                                <Link to="/login"
                                    className="text-decoration-none fw-bold ms-2" >Sign In</Link>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </>
    )

}

export default SignUp;