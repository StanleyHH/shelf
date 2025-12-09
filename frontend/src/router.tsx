import { createBrowserRouter } from 'react-router';

import Layout from './components/Layout.tsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <div>Hello main content</div>
      },
    ],
  },
]);

export default router;
