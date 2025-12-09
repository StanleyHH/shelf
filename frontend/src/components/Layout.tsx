import FilterSidebar from './FilterSidebar.tsx';
import Header from './Header.tsx';
import MainSidebar from './MainSidebar.tsx';
import PageContent from './PageContent.tsx';

export default function Layout() {
  return (
    <>
      <Header />
      <MainSidebar />
      <PageContent />
      <FilterSidebar />
    </>
  );
}
