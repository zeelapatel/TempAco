import React from 'react';
import { Card, Col, Row } from 'antd';

const PropertyGrid = React.memo(({ properties }) => {
  const pageSize = 9; // For a 3x3 grid
  const numEmptyCards = pageSize - properties.length;

  // Fill the grid with empty cards if there are fewer properties than the page size
  const allProperties = [...properties, ...Array(numEmptyCards).fill(null)];

  return (
    <Row gutter={[16, 16]} justify="center">
      {allProperties.map((property, index) => (
        <Col span={8} key={index} style={{ minWidth: 250 }}>
          {property ? (
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
          ) : (
            <Card
              hoverable
              style={{ height: '100%' }}
              cover={<div style={{ height: '140px', backgroundColor: '#f0f0f0' }} />}
            />
          )}
        </Col>
      ))}
    </Row>
  );
});

export default PropertyGrid;
