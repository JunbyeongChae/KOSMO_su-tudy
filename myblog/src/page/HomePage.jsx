import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="text-3xl">
      <div className="flex">
        <Link to="/about">About</Link>
      </div>
      <h1>HomePage</h1>
    </div>
  );
};

export default HomePage;
