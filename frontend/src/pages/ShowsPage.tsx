import Breadcrumb from '../components/Breadcrumb.tsx';
import ShowGrid from '../components/ShowGrid.tsx';

export default function ShowsPage() {
  return (
    <>
      <Breadcrumb />
      <p className="mt-2 text-3xl">TV Shows</p>
      <ShowGrid />
    </>
  );
}
