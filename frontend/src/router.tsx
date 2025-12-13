import { Navigate, createBrowserRouter } from 'react-router';

import Layout from './pages/Layout.tsx';
import ShowsPage from './pages/ShowsPage.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    errorElement: <Layout />,
    children: [
      {
        index: true,
        element: <Navigate to="/shows" replace />,
      },
      {
        path: 'shows',
        element: <ShowsPage />,
      },
    ],
  },
]);

export default router;
