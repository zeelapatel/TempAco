import React, { useEffect, useState } from 'react';
import { Button } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "../../styles/HomePageStyles.css";
import PropertyListing from '../components/PropertyListing';
import logo from '../images/logo.png';
import HomePageBackgroundImage from '../components/HomePageBackgroundImage';

// Utility function for fetching properties
const fetchProperties = async (setProperties, setLoading) => {
  setLoading(true);
  try {
    const response = await axios.get('http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/property/listing');
    setProperties(response.data);
  } catch (error) {
    console.error("There was an error fetching the property listings!", error);
  } finally {
    setLoading(false);
  }
};

const HomePage = () => {
  const [properties, setProperties] = useState([]);
  const [user, setUser] = useState(null);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  // Handle user authentication and fetch properties
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const userData = localStorage.getItem('firstName');
      setUser({ name: userData });
    }
    fetchProperties(setProperties, setLoading);
  }, []);

  // Handle Logout
  const handleLogout = async () => {
    try {
      const response = await axios.post('http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/logout', {}, {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
  
      if (response.status === 200) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setUser(null);
        navigate('/login');
      } else {
        console.log("Unexpected response status:", response.status);
      }
    } catch (error) {
      console.error("There was an error logging out:", error);
    }
  };

  const handleDemoLogin = async () => {
    const demoCredentials = {
      email: "demo1234@example.com",
      password: "12345"
    };
  
    try {
      const res = await axios.post("http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/signin", demoCredentials);
      
      if (res.status === 200 && res.data.token) {
        localStorage.setItem("token", res.data.token);
        localStorage.setItem("firstName", res.data.firstName);
        localStorage.setItem("lastName", res.data.lastName);
        localStorage.setItem("email", res.data.email);
        
        setUser({ name: res.data.firstName });
        navigate("/");
      } else {
        console.log("Demo Login Failure");
      }
    } catch (error) {
      console.error("Error during demo login:", error);
    }
  };
  return (
    <div>
      <Navbar user={user} onLogout={handleLogout} onAddProperty={() => navigate('/addProperty')}onDemoLogin={handleDemoLogin} />
      <HomePageBackgroundImage />
      {/* Property Listing */}
      <div className="property-listing">
        <PropertyListing properties={properties} loading={loading} />
      </div>
    </div>
  );
};

// Navbar Component
const Navbar = React.memo(({ user, onLogout, onAddProperty, onDemoLogin }) => {
  const navigate = useNavigate();
  return (
    <div className="navbar">
      <div className="navbar-logo">
        <img src={logo} alt="Logo" onClick={() => navigate('/')} />
      </div>
      <div className="auth-buttons">
        {!user ? (
          <>
            <Button type="primary" onClick={() => navigate('/login')}>Login</Button>
            <Button type="primary" onClick={onDemoLogin}>Demo Login</Button>
            <Button type="primary" onClick={() => navigate('/register')}>Signup</Button>
          </>
        ) : (
          <div className="user-info">
            <span>Welcome, {user.name}</span>
            <Button onClick={onAddProperty}>Add Property</Button>
            <Button onClick={onLogout}>Sign Out</Button>
            <Button onClick={() => navigate('/userProfile')}>Profile</Button>
          </div>
        )}
      </div>
    </div>
  );
});
export default HomePage;
