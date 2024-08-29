import React, { useState, useEffect } from 'react';
import { Form, Input, Button, message, Spin } from 'antd';
import axios from 'axios';
import '../../styles/ProfilePageStyles.css';  // Import the CSS file
import { useNavigate } from 'react-router-dom';

const ProfilePage = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [editing, setEditing] = useState(false);
  const navigate = useNavigate();

  // Fetch user information on component mount
  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get('http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/users/me', {
          headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
        });
        setUser(response.data);
      } catch (error) {
        console.error('Error fetching user data:', error);
        message.error('Failed to load user data.');
        navigate('/login')
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  // Update user information
  const handleUpdate = async (values) => {
    try {
      const response = await axios.put('/users/updateUserInfo', values, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` },
      });
      setUser(response.data);
      message.success('User information updated successfully!');
      setEditing(false);
    } catch (error) {
      console.error('Error updating user data:', error);
      message.error('Failed to update user information.');
    }
  };

  if (loading) {
    return <Spin size="large" style={{ marginTop: '50px', display: 'block', textAlign: 'center' }} />;
  }

  return (
    <div className="profile-container">
      <h2>User Profile</h2>
      {!editing ? (
        <>
          <p><strong>First Name:</strong> {user.firstName}</p>
          <p><strong>Last Name:</strong> {user.lastName}</p>
          <p><strong>Email:</strong> {user.email}</p>
          <Button type="primary" onClick={() => setEditing(true)}>Edit Profile</Button>
        </>
      ) : (
        <Form
          name="userProfile"
          initialValues={user}
          onFinish={handleUpdate}
          layout="vertical"
        >
          <Form.Item
            label="First Name"
            name="firstName"
            rules={[{ required: true, message: 'Please enter your first name!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Last Name"
            name="lastName"
            rules={[{ required: true, message: 'Please enter your last name!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            label="Email"
            name="email"
            rules={[{ required: true, message: 'Please enter your email!', type: 'email' }]}
          >
            <Input disabled />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Update</Button>
            <Button onClick={() => setEditing(false)}>Cancel</Button>
          </Form.Item>
        </Form>
      )}
    </div>
  );
};

export default ProfilePage;
