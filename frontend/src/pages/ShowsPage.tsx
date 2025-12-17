import Breadcrumb from '../components/Breadcrumb.tsx';
import SecondarySidebarContainer from '../components/SecondSidebarContainer.tsx';
import ShowGrid from '../components/ShowGrid.tsx';
import ShowsFilter from '../components/ShowsFilter.tsx';
import ShowsPageSearch from '../components/ShowsPageSearch.tsx';
import ShowsPageTitel from '../components/ShowsPageTitel.tsx';

export default function ShowsPage() {
  return (
    <>
      <Breadcrumb />
      <ShowsPageTitel />
      <ShowsPageSearch />
      <ShowGrid />
      <SecondarySidebarContainer>
        <ShowsFilter />
      </SecondarySidebarContainer>
    </>
  );
}
