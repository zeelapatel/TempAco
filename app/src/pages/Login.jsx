import React from "react";
import "../../styles/LoginStyles.css";
import { Form, Input, message } from "antd";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

const Login = () => {
  const navigate = useNavigate();
  const onfinishHandler = async (values) => {
    try {
      const res = await axios.post("http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/signin", values);
      console.log(res)
      if (res.status === 200 && res.data.token) {
        // Store token in localStorage
        localStorage.setItem("token", res.data.token);
        localStorage.setItem("firstName", res.data.firstName);
        localStorage.setItem("lastName", res.data.lastName);
        localStorage.setItem("email", res.data.email);
        message.success("Login Successfully");

        navigate("/");
      } else {
        // If login fails
        console.log("Login Failure");
        message.error(res.data.message || "Login Failed");
      }
    } catch (error) {
      console.log(error);
      message.error("something went wrong");
    }
  };
  return (
    <div className="form-container ">
      <Form
        layout="vertical"
        onFinish={onfinishHandler}
        className="register-form"
      >
        <h3 className="text-center">Login From</h3>

        <Form.Item label="Email" name="email">
          <Input type="email" required />
        </Form.Item>
        <Form.Item label="Password" name="password">
          <Input type="password" required />
        </Form.Item>
        <Link to="/register" className="m-2">
          Not a user Register here
        </Link>
        <button className="btn btn-primary" type="submit">
          Login
        </button>
      </Form>
    </div>
  );
};

export default Login;