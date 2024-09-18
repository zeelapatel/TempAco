import React, { useState } from 'react';
import { Typography, Input, Button, DatePicker } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import '../../styles/HomePageBackgroundImage.css';
import moment from 'moment';
import homePageBackground from '../images/guillaume-bolduc-uZc-QuBvjmU-unsplash.jpg';

const { Title, Text } = Typography;

const HeroImage = () => {
  const [location, setLocation] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [bed, setBed] = useState('');
  const [bath, setBath] = useState('');
  const[moveInDate,setMoveInDate] = useState(null);
  const navigate = useNavigate();

  const onSearch = () => {
    const searchParams = new URLSearchParams({
      location,
      minPrice,
      maxPrice,
      bed,
      bath,
      moveInDate: moveInDate? moveInDate.format("YYYY-MM-DD"):''
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
          <div className= "text-container">
            <Text className="search-label">City</Text>
            </div>
            <Input
              placeholder="Location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Min Price Input */}
          <div className="search-field">
          <div className= "text-container">
            <Text className="search-label">Min Price</Text>
            </div>
            <div>
            <Input
              placeholder="Min Price"
              type="number"
              value={minPrice}
              onChange={(e) => setMinPrice(e.target.value)}
              className="search-bar"
            />
            </div>
          </div>
          {/* Max Price Input */}
          <div className="search-field">
          <div className= "text-container">
            <Text className="search-label">Max Price</Text>
            </div>
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
          <div className= "text-container">
            <Text className="search-label">Bedrooms</Text>
            </div>
            <Input
              placeholder="Bedrooms"
              value={bed}
              onChange={(e) => setBed(e.target.value)}
              className="search-bar"
            />
          </div>
          {/* Bathrooms Input */}
          <div className="search-field">
          <div className= "text-container">
            <Text className="search-label">Bathrooms</Text>
            </div>
            <Input
              placeholder="Bathrooms"
              value={bath}
              onChange={(e) => setBath(e.target.value)}
              className="search-bar"
            />
          </div>
          
          <div className="search-field">
          <div className= "text-container">
            <Text className="search-label">Move In Date</Text>
            </div>
            <DatePicker
              placeholder="Date"
              value={moveInDate}
              onChange={(date) => setMoveInDate(date)}
              className="date-picker"
            />
          </div>
          
          
          {/* Search Button */}
          
          
        </div>
        <Button className="search-btn"
              icon={<SearchOutlined />}
              size="large"
              onClick={onSearch}
            >
              Search
            </Button>
      </div>
    </div>
  );
};

export default HeroImage;
