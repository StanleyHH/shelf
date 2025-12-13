import useCountries from '../hooks/useCountries.ts';
import useGenres from '../hooks/useGenres.ts';
import useShowQueryStore from '../store.ts';
import SearchFilter from './searchFilter/SearchFilter.tsx';

export default function FilterSidebar() {
  const { data: genres = [] } = useGenres();
  const selectedGenreName = useShowQueryStore((s) => s.showQuery.genreName);
  const setSelectedGenreName = useShowQueryStore((s) => s.setGenreName);

  const { data: countries = [] } = useCountries();
  const selectedCountryName = useShowQueryStore((s) => s.showQuery.countryName);
  const setSelectedCountryName = useShowQueryStore((s) => s.setCountryName);

  return (
    <aside
      className="scrollbar-none fixed top-(--sidebar-y-indent)
        right-(--sidebar-x-indent) h-(--sidebar-height)
        w-(--filter-sidebar-width) overflow-y-auto border-l-2 border-gray-150
        bg-white p-5"
    >
      <SearchFilter
        filterName="Genre"
        items={genres}
        selected={selectedGenreName}
        onSelect={setSelectedGenreName}
      />
      <SearchFilter
        filterName="Country of origin"
        items={countries}
        selected={selectedCountryName}
        onSelect={setSelectedCountryName}
      />
    </aside>
  );
}
