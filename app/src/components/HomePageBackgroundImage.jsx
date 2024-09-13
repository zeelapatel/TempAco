// HeroImage.js
import React from 'react';
import { Typography, Input } from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import '../../styles/HomePageBackgroundImage.css';
import homePageBackground from '../images/home_page_back.jpg';

const { Title } = Typography;
const { Search } = Input;

const HeroImage = () => {
  const onSearch = (value) => console.log(value);

  return (
    <div className="hero-image" style={{ backgroundImage: `url(${homePageBackground})` }}>
      <div className="hero-content">
        <Title class="text" level={1}>Find Your Dream Property</Title>
        <Search
          placeholder="Search for properties..."
          enterButton={<SearchOutlined />}
          size="large"
          onSearch={onSearch}
          className="search-bar"
        />
      </div>
    </div>
  );
};

export default HeroImage;