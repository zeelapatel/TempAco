import React, { useState } from "react";
import { Form, Input, InputNumber, DatePicker, Button, Upload, message } from "antd";
import { UploadOutlined } from '@ant-design/icons';
import axios from "axios";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import "../../styles/AddPropertyStyles.css";
const AddProperty = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [file, setFile] = useState(null);
  const navigate = useNavigate();

  const onFinish = async (values) => {
    const formData = new FormData();

    formData.append("title", values.title);
    formData.append("description", values.description);
    formData.append("address", values.address);
    formData.append("zip", values.zip);
    formData.append("price", values.price);
    formData.append("bed", values.bed);
    formData.append("bath", values.bath);
    formData.append("moveInDate", values.moveInDate.format("YYYY-MM-DD"));
    formData.append("moveOutDate", values.moveOutDate.format("YYYY-MM-DD"));
    console.log(formData.moveInDate);
    if (file) {
      formData.append("photo", file);
    }
    // Append file to formData
    if (values.photo) {
      formData.append("photo", values.photo.file.originFileObj);
    }

    try {
      setLoading(true);
      
      const response = await axios.post("http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/property/addProperty", formData, {
        
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${localStorage.getItem("token")}`, // Attach the token for authorization
        },
      });
      if (response.status === 200) {
        message.success("Property added successfully!");
        navigate("/"); // Navigate to another page, like the home page or property list
      } else {
        message.error("Failed to add property");
      }
    } catch (error) {
      console.error("Error adding property:", error);
      message.error("Something went wrong");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <h2>Add New Property</h2>
      <Form
        form={form}
        layout="vertical"
        onFinish={onFinish}
        initialValues={{
          price: 0,
          bed: 1,
          bath: 1,
        }}
      >
        <Form.Item label="Title" name="title" rules={[{ required: true, message: "Please enter the property title" }]}>
          <Input />
        </Form.Item>
        
        <Form.Item label="Description" name="description" rules={[{ required: true, message: "Please enter the property description" }]}>
          <Input.TextArea rows={4} />
        </Form.Item>

        <Form.Item label="Address" name="address" rules={[{ required: true, message: "Please enter the property address" }]}>
          <Input />
        </Form.Item>

        <Form.Item label="ZIP Code" name="zip" rules={[{ required: true, message: "Please enter the ZIP code" }]}>
          <Input />
        </Form.Item>

        <Form.Item label="Price" name="price" rules={[{ required: true, message: "Please enter the property price" }]}>
          <InputNumber min={0} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item label="Bedrooms" name="bed" rules={[{ required: true, message: "Please enter the number of bedrooms" }]}>
          <InputNumber min={1} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item label="Bathrooms" name="bath" rules={[{ required: true, message: "Please enter the number of bathrooms" }]}>
          <InputNumber min={1} step={0.5} style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item label="Move-in Date" name="moveInDate" rules={[{ required: true, message: "Please select the move-in date" }]}>
          <DatePicker style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item label="Move-out Date" name="moveOutDate" rules={[{ required: true, message: "Please select the move-out date" }]}>
          <DatePicker style={{ width: "100%" }} />
        </Form.Item>

        <Form.Item label="Property Photo" name="photo" rules={[{ required: true, message: "Please upload a photo of the property" }]}>
          <Upload 
            listType="picture" 
            maxCount={1}
            beforeUpload={(file) => {
              setFile(file);
              return false;  // Prevent automatic upload
            }}>
            <Button icon={<UploadOutlined />}>Click to Upload</Button>
          </Upload>
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            Add Property
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
};

export default AddProperty;
