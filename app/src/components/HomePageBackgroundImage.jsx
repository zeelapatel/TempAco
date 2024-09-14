import React, { useState } from 'react';
import { Typography, Input, Button } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import '../../styles/HomePageBackgroundImage.css';
import homePageBackground from '../images/home_page_back.jpg';

const { Title, Text } = Typography;

const HeroImage = () => {
  const [location, setLocation] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [bed, setBed] = useState('');
  const [bath, setBath] = useState('');

  const navigate = useNavigate();

  const onSearch = () => {
    const searchParams = new URLSearchParams({
      location,
      minPrice,
      maxPrice,
      bed,
      bath,
    }).toString();

    navigate(`/searchResults?${searchParams}`);
  };

  return (
    <div className="hero-image" style={{ backgroundImage: `url(${homePageBackground})` }}>
      <div className="hero-content">
        <Title className="text" level={1}>Find Your Dream Property</Title>
        <div className="search-parameters">
          {/* Location Input */}
          <div className="search-field">
            <Text className="search-label">Location</Text>
            <Input
              placeholder="Location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Min Price Input */}
          <div className="search-field">
            <Text className="search-label">Min Price</Text>
            <Input
              placeholder="Min Price"
              type="number"
              value={minPrice}
              onChange={(e) => setMinPrice(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Max Price Input */}
          <div className="search-field">
            <Text className="search-label">Max Price</Text>
            <Input
              placeholder="Max Price"
              type="number"
              value={maxPrice}
              onChange={(e) => setMaxPrice(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Bedrooms Input */}
          <div className="search-field">
            <Text className="search-label">Bedrooms</Text>
            <Input
              placeholder="Bedrooms"
              value={bed}
              onChange={(e) => setBed(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Bathrooms Input */}
          <div className="search-field">
            <Text className="search-label">Bathrooms</Text>
            <Input
              placeholder="Bathrooms"
              value={bath}
              onChange={(e) => setBath(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Property Type Input */}
          
          {/* Search Button */}
          <div className="search-field">
            <Button className="search-btn"
            //   type="primary"
              icon={<SearchOutlined />}
              size="large"
              onClick={onSearch}
            //   className="search-bar"
            >
              Search
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HeroImage;
