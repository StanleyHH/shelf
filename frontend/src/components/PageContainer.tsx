import { Outlet, useRouteError } from 'react-router';

import ErrorPage from '../pages/ErrorPage.tsx';

export default function PageContainer() {
  const error = useRouteError();

  return (
    <main
      className="mt-(--headbar-height) ml-(--page-container-indent)
        min-h-(--sidebar-height) w-(--page-container-width) bg-white p-5"
    >
      {error ? <ErrorPage /> : <Outlet />}
    </main>
  );
}
