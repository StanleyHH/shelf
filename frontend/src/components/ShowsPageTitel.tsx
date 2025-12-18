import useShowQueryStore from '../store.ts';

export default function ShowsPageTitel() {
  const genre = useShowQueryStore((s) => s.showQuery.genreName);
  const country = useShowQueryStore((s) => s.showQuery.countryName);
  const year = useShowQueryStore((s) => s.showQuery.year);

  return (
    <p className="mt-2 text-2xl">
      {country && <span>{country} -</span>} {genre} TV Shows{' '}
      {year && <span>of {year}</span>}
    </p>
  );
}
