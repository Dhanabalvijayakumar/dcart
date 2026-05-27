import React from "react";
import SignUp from "./components/SignUp";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import SignUpLanding from "./components/SignUpLanding";
import Products from "./components/Products";
import ProtectedLayout from "./components/ProtectedLayout";
import Cart from "./components/Cart";
import Wishlist from "./components/Wishlist";
import Orders from "./components/Orders";
import Login from "./components/Login";
import SignUpCheck from "./components/SignUpCheck";
import ProductDetails from "./components/ProductDetails";



function App() {

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signupCheck" element={<SignUpCheck />} />
          <Route path="signupLanding" element={<SignUpLanding />} />
          <Route path="/signup" element={<SignUp />} />

          {/* <Route path="/navbar" element={<Navbar />} /> */}
          <Route element={<ProtectedLayout />} >
            <Route path="/products" element={<Products />} />
            <Route path="/product/:id" element={<ProductDetails />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/wishlist" element={<Wishlist />} />
            <Route path="/orders" element={<Orders />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  )

}

export default App;