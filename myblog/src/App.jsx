import React from 'react';
import { RouterProvider } from 'react-router-dom';
import root from './route/root';

const App = () => {
  return (
    <>
      <RouterProvider router={root} />
    </>
  );
};

export default App;
