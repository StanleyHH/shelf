import FilterSidebar from './components/FilterSidebar.tsx';
import Header from './components/Header.tsx';
import MainSidebar from './components/MainSidebar.tsx';
import PageContent from './components/PageContent.tsx';

export default function App() {
  return (
    <>
      <Header />
      <MainSidebar />
      <PageContent />
      <FilterSidebar />
    </>
  );
}
