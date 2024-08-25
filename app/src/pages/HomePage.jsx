import React, { useEffect, useState } from 'react';
import { Button, Card, Col, Row } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "../../styles/HomePageStyles.css";

const HomePage = () => {
  const [properties, setProperties] = useState([]);
  const [user, setUser] = useState(null);
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

  return (
    <div>
      {/* Navigation Bar */}
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
              <Button onClick={handleLogout}>Sign Out</Button>
            </div>
          )}
        </div>
      </div>

      {/* Property Listing */}
      <div className="property-listing">
        <Row gutter={[16, 16]}>
          {properties.map(property => (
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
      </div>
    </div>
  );
}

export default HomePage;