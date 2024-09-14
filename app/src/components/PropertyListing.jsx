import React, { useState, useCallback } from 'react';
import { Card, Col, Row, Spin,Typography  } from 'antd';
import "../../styles/PropertyListingStyles.css"; // Make sure to create this CSS file for styling
const { Title } = Typography;
// PropertyListing Component to display properties and handle pagination
const PropertyListing = ({ properties, loading }) => {
  const pageSize = 3; 
  const [currentPage, setCurrentPage] = useState(1);

  // Handle page change
  const handlePageChange = useCallback((page) => {
    setCurrentPage(page);
  }, []);

  // Calculate properties to display on the current page
  const indexOfLastProperty = currentPage * pageSize;
  const indexOfFirstProperty = indexOfLastProperty - pageSize;
  const currentProperties = properties.slice(indexOfFirstProperty, indexOfLastProperty);

  // Calculate total number of pages
  const totalPages = Math.ceil(properties.length / pageSize);

  return (
    <div>
      {loading ? (
        <Spin size="large" style={{ marginTop: '50px' }} />
      ) : (
        <>
        <Title level={2} className="listing-title">Our Top Listing</Title>
          <Row gutter={[16, 16]} justify="center">
            {currentProperties.map((property, index) => (
              <Col span={6} key={index} style={{ minWidth: 250 }}>
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

          {/* Dots Pagination */}
          <div className="dots-pagination">
            {Array.from({ length: totalPages }).map((_, index) => (
              <span
                key={index}
                className={`dot ${currentPage === index + 1 ? 'active' : ''}`}
                onClick={() => handlePageChange(index + 1)}
              ></span>
            ))}
          </div>
        </>
      )}
    </div>
  );
};

export default PropertyListing;
