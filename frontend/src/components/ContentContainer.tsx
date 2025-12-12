import { Outlet, useRouteError } from 'react-router';

import ErrorPage from '../pages/ErrorPage.tsx';

export default function ContentContainer() {
  const error = useRouteError();

  return (
    <main
      className="mt-(--headbar-height) ml-(--content-container-indent)
        min-h-(--sidebar-height) w-(--content-container-width) bg-white p-5"
    >
      {error ? <ErrorPage /> : <Outlet />}
    </main>
  );
}
