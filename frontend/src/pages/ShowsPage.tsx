import Breadcrumb from '../components/Breadcrumb.tsx';
import ShowGrid from '../components/ShowGrid.tsx';
import ShowsPageTitel from '../components/ShowsPageTitel.tsx';

export default function ShowsPage() {
  return (
    <>
      <Breadcrumb />
      <ShowsPageTitel />
      <ShowGrid />
    </>
  );
}
