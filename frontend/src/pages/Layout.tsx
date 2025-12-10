import FilterSidebar from '../components/FilterSidebar.tsx';
import Header from '../components/Header.tsx';
import MainSidebar from '../components/MainSidebar.tsx';
import PageContainer from '../components/PageContainer.tsx';

export default function Layout() {
  return (
    <>
      <Header />
      <MainSidebar />
      <PageContainer />
      <FilterSidebar />
    </>
  );
}
