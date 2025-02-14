import { lazy, Suspense } from 'react';
import { createBrowserRouter } from 'react-router-dom';

const Home = lazy(() => import('../page/HomePage'));
const About = lazy(() => import('../page/AboutPage'));
const Loading = <div className={'bg-red-600'}>Loading...</div>;

const root = createBrowserRouter([
  {
    path: '/',
    element: (
      <Suspense fallback={Loading}>
        <Home />
      </Suspense>
    )
  },
  {
    path: '/about',
    element: (
      <Suspense fallback={Loading}>
        <About />
      </Suspense>
    )
  }
]);

export default root;
