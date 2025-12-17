import { createBrowserRouter } from 'react-router';

import Layout from './pages/Layout.tsx';
import ShowDetailsPage from './pages/ShowDetailsPage.tsx';
import ShowsPage from './pages/ShowsPage.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    errorElement: <Layout />,
    children: [
      {
        index: true,
        element: <ShowsPage />,
      },
      {
        path: 'shows',
        element: <ShowsPage />,
      },
      {
        path: 'shows/:id',
        element: <ShowDetailsPage />,
      },
    ],
  },
]);

export default router;
