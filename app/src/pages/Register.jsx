import React from "react";
import "../../styles/RegiserStyles.css";
import { Form, Input, message } from "antd";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
const Register = () => {
  const navigate = useNavigate();

  //form handler
  const onfinishHandler = async (values) => {
    try {
      const res = await axios.post("http://localhost:8080/api/v1/signup", values);
      console.log(res);
      if (res.data.token && res.status == 200) {
        console.log("Register success");
        message.success("Register Successfully!");
        navigate("/login");
      } else {
        message.error(res.data.message);
      }
    } catch (error) {
      console.log(error);
      message.error("Something Went Wrong");
    }
  };
  return (
    <>
      <div className="form-container ">
        <Form
          layout="vertical"
          onFinish={onfinishHandler}
          className="register-form"
        >
          <h3 className="text-center">Register From</h3>
          <Form.Item label="First Name" name="firstName">
            <Input type="text" required />
          </Form.Item>
          <Form.Item label="Last Name" name="lastName">
            <Input type="text" required />
          </Form.Item>
          <Form.Item label="Email" name="email">
            <Input type="email" required />
          </Form.Item>
          <Form.Item label="Password" name="password">
            <Input type="password" required />
          </Form.Item>
          <Link to="/login" className="m-2">
            Already user login here
          </Link>
          <button className="btn btn-primary" type="submit">
            Register
          </button>
        </Form>
      </div>
    </>
  );
};

export default Register;