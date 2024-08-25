import React, { useEffect, useState } from 'react';
import { Button, Card, Col, Row, Pagination } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "../../styles/HomePageStyles.css";

const HomePage = () => {
  const [properties, setProperties] = useState([]);
  const [user, setUser] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize] = useState(6); // Number of properties per page
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      // Fetch user details from token
      const userData = localStorage.getItem('firstName');
      setUser({ name: userData });
    }

    // Fetch property listings
    axios.get('http://localhost:8080/api/v1/property/listing')
    .then(response => {
      setProperties(response.data);
    })
    .catch(error => {
      console.error("There was an error fetching the property listings!", error);
    });
  }, []);

  const handleLogout = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/v1/logout', {}, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        }
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

  // Handle page change
  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  // Calculate properties to display on the current page
  const indexOfLastProperty = currentPage * pageSize;
  const indexOfFirstProperty = indexOfLastProperty - pageSize;
  const currentProperties = properties.slice(indexOfFirstProperty, indexOfLastProperty);

  return (
    <div class="home-page">
      {/* Navigation Bar */}
      <div className="navbar">
        <div className="auth-buttons">
          {!user ? (
            <>
              <Button  type="primary" onClick={() => navigate('/login')}>Login</Button>
              <Button type="primary" onClick={() => navigate('/register')}>Signup</Button>
              
            </>
            
          ) : (
            <div className="user-info">
              <span>Welcome, {user.name}</span>
              <Button onClick={handleLogout}>Sign Out</Button>
              <Button  >Profile</Button>
            
            </div>
          )}
        </div>
      </div>

      {/* Property Listing */}
      <div className="property-listing">
        <Row gutter={[16, 16]}>
          {currentProperties.map(property => (
            <Col span={8} key={property.id}>
              <Card
                hoverable
                cover={<img alt={property.title} src={`data:image/jpeg;base64,${property.photo}`} />}
              >
                <Card.Meta 
                  title={property.title} 
                  description={`$${property.price} - ${property.bed} bed(s), ${property.bath} bath(s)`} 
                />
                <p>{property.address}</p>
                <p>Move In: {new Date(property.moveInDate).toLocaleDateString()}</p>
                <p>Move Out: {new Date(property.moveOutDate).toLocaleDateString()}</p>
              </Card>
            </Col>
          ))}
        </Row>

        {/* Pagination Component */}
        <Pagination 
          current={currentPage} 
          pageSize={pageSize} 
          total={properties.length} 
          onChange={handlePageChange} 
          style={{ textAlign: 'center', marginTop: '20px' }}
        />
      </div>
    </div>
  );
}

export default HomePage;
