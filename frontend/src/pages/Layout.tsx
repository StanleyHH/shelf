import Header from '../components/Header.tsx';
import MainPage from '../components/MainPage.tsx';
import MainSidebar from '../components/MainSidebar.tsx';

export default function Layout() {
  return (
    <>
      <Header />
      <MainSidebar />
      <MainPage />
    </>
  );
}
