import { createBrowserRouter } from 'react-router';

import ActorDetailsPage from './pages/ActorDetailsPage.tsx';
import EpisodeDetailsPage from './pages/EpisodeDetailsPage.tsx';
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
        path: 'people/:id',
        element: <ActorDetailsPage />,
      },
      {
        path: 'shows',
        children: [
          {
            index: true,
            element: <ShowsPage />,
          },
          {
            path: ':showId',
            children: [
              {
                index: true,
                element: <ShowDetailsPage />,
              },
              {
                path: 'episodes/:episodeId',
                element: <EpisodeDetailsPage />,
              },
            ],
          },
        ],
      },
    ],
  },
]);

export default router;
