import { Outlet } from 'react-router';

export default function PageContainer() {
  return (
    <main
      className="mt-(--headbar-height) ml-(--page-container-indent)
        min-h-(--sidebar-height) w-(--page-container-width) bg-white"
    >
      <Outlet />
    </main>
  );
}
