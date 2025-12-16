import { Outlet, useRouteError } from 'react-router';

import ErrorPage from '../pages/ErrorPage.tsx';

export default function MainPage() {
  const error = useRouteError();

  return (
    <main
      className="mt-(--headbar-height) ml-(--content-container-indent)
        min-h-(--sidebar-height) w-(--content-container-width) bg-white p-5
        pb-100"
    >
      {error ? <ErrorPage /> : <Outlet />}
    </main>
  );
}
