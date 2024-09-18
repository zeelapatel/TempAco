import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import { List, Card, Typography, Spin, Button } from 'antd';
import '../../styles/SearchResults.css';

const { Title } = Typography;

const SearchResults = () => {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);

  useEffect(() => {
    const fetchProperties = async () => {
      setLoading(true);
      setError(null);

      // Construct params object with only non-empty values
      const params = {};
      const keys = ['location', 'minPrice', 'maxPrice', 'bed', 'bath'];
      keys.forEach(key => {
        const value = queryParams.get(key);
        if (value) params[key] = value;
      });

      try {
        const response = await axios.get('http://tempaco-v2-env.eba-axzkac2g.eu-north-1.elasticbeanstalk.com/api/v1/property/search', { params });
        setProperties(response.data);
      } catch (err) {
        setError('Failed to fetch properties');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProperties();
  }, [location.search]);

  if (loading) return <Spin size="large" />;
  if (error) return <div>{error}</div>;

  return (
    <div className="search-results">
      <Title level={2}>Search Results</Title>
      <List
        grid={{ gutter: 16, column: 4 }}
        dataSource={properties}
        renderItem={item => (
          <List.Item>
            <Card
              cover={item.photo ? <img alt={item.title} src={`data:image/jpeg;base64,${item.photo}`} /> : <div>No Image Available</div>}
            >
              <Card.Meta
                title={item.title}
                description={
                  <>
                    <p>Location: {item.address}</p>
                    <p>Price: ${item.price}</p>
                    <p>Bedrooms: {item.bed}</p>
                    <p>Bathrooms: {item.bath}</p>
                    <p>Type: {item.propertyType}</p>
                    <p>Move In: {new Date(item.moveInDate).toLocaleDateString()}</p>
                    <p>Move Out: {new Date(item.moveOutDate).toLocaleDateString()}</p>
                  </>
                }
              />
              <Button type="primary">View Details</Button>
            </Card>
          </List.Item>
        )}
      />
    </div>
  );
};

export default SearchResults;
