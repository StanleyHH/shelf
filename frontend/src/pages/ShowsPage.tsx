import ShowCard from '../components/ShowCard.tsx';

export default function ShowsPage() {
  return (
    <>
      <p className="text-3xl">TV Shows</p>
      <div className="mt-5 grid grid-cols-2 gap-5 md:grid-cols-4">
        <ShowCard status="ONGOING" />
        <ShowCard status="ON_BREAK" />
        <ShowCard status="ENDED" />
      </div>
    </>
  );
}
