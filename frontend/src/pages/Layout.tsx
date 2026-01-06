import { useEffect } from 'react';

import { useAuthStore } from '../authStore.ts';
import Header from '../components/Header.tsx';
import MainPage from '../components/MainPage.tsx';
import MainSidebar from '../components/MainSidebar.tsx';

export default function Layout() {
  useEffect(() => {
    (async () => {
      await useAuthStore.getState().fetchUser();
    })();
  }, []);
  return (
    <>
      <Header />
      <MainSidebar />
      <MainPage />
    </>
  );
}
