import { Outlet } from 'react-router';

export default function PageContent() {
  return (
    <main
      className="mt-(--headbar-height) ml-(--page-content-indent)
        min-h-(--sidebar-height) w-(--page-content-width) bg-white"
    >
      <Outlet />
    </main>
  );
}
