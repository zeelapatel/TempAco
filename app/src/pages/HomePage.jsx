import React, { useEffect, useState, useCallback } from 'react';
import { Button, Card, Col, Row, Pagination, Spin } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "../../styles/HomePageStyles.css";
import PropertyGrid from './PropertyGrid';

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
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 6; // Number of properties per page
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);

  // Handle user authentication and fetch properties
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      // Fetch user details from token
      const userData = localStorage.getItem('firstName');
      setUser({ name: userData });
    }
    // Fetch property listings
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

  // Memoized function to handle page change to prevent unnecessary re-renders
  const handlePageChange = useCallback((page) => {
    setCurrentPage(page);
  }, []);

  // Calculate properties to display on the current page
  const indexOfLastProperty = currentPage * pageSize;
  const indexOfFirstProperty = indexOfLastProperty - pageSize;
  const currentProperties = properties.slice(indexOfFirstProperty, indexOfLastProperty);

  return (
    <div>
      <Navbar user={user} onLogout={handleLogout} onAddProperty={() => navigate('/addProperty')} />
      
      {/* Property Listing */}
      <div className="property-listing">
        {loading ? (
          <Spin size="large" style={{ marginTop: '50px' }} />
        ) : (
          <>
            <PropertyGrid properties={currentProperties} />
            <Pagination 
              current={currentPage} 
              pageSize={pageSize} 
              total={properties.length} 
              onChange={handlePageChange} 
             
            />
          </>
        )}
      </div>
    </div>
  );
};

// Navbar Component
const Navbar = React.memo(({ user, onLogout, onAddProperty }) => {
  const navigate = useNavigate();
  return (
    <div className="navbar">
      <div className="auth-buttons">
        {!user ? (
          <>
            <Button type="primary" onClick={() => navigate('/login')}>Login</Button>
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
