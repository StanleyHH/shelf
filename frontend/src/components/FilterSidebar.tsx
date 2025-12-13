import { STATUSES } from '../entities/Status.ts';
import { YEARS } from '../entities/Year.ts';
import useCountries from '../hooks/useCountries.ts';
import useGenres from '../hooks/useGenres.ts';
import useShowQueryStore from '../store.ts';
import SearchFilter from './searchFilter/SearchFilter.tsx';
import SearchFilterSelectedItem from './searchFilter/SearchFilterSelectedItem.tsx';

export default function FilterSidebar() {
  const { data: genres = [] } = useGenres();
  const selectedGenreName = useShowQueryStore((s) => s.showQuery.genreName);
  const setSelectedGenreName = useShowQueryStore((s) => s.setGenreName);

  const { data: countries = [] } = useCountries();
  const selectedCountryName = useShowQueryStore((s) => s.showQuery.countryName);
  const setSelectedCountryName = useShowQueryStore((s) => s.setCountryName);

  const selectedStatus = useShowQueryStore((s) => s.showQuery.status);
  const setSelectedStatus = useShowQueryStore((s) => s.setStatus);

  const selectedYear = useShowQueryStore((s) => s.showQuery.year);
  const setSelectedYear = useShowQueryStore((s) => s.setYear);

  return (
    <aside
      className="scrollbar-none fixed top-(--sidebar-y-indent)
        right-(--sidebar-x-indent) h-(--sidebar-height)
        w-(--filter-sidebar-width) overflow-y-auto border-l-2 border-gray-150
        bg-white px-5 pt-1"
    >
      {(selectedGenreName ||
        selectedCountryName ||
        selectedYear ||
        selectedStatus) && (
        <div className="flex flex-wrap gap-3 pt-5">
          {selectedGenreName && (
            <SearchFilterSelectedItem
              selected={selectedGenreName}
              onClick={() => setSelectedGenreName('')}
            />
          )}
          {selectedCountryName && (
            <SearchFilterSelectedItem
              selected={selectedCountryName}
              onClick={() => setSelectedCountryName('')}
            />
          )}
          {selectedYear && (
            <SearchFilterSelectedItem
              selected={selectedYear}
              onClick={() => setSelectedYear('')}
            />
          )}
          {selectedStatus && (
            <SearchFilterSelectedItem
              selected={
                STATUSES.find((status) => status.id === selectedStatus)?.name
              }
              onClick={() => setSelectedStatus('')}
            />
          )}
        </div>
      )}

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
      <SearchFilter
        filterName="Released"
        items={YEARS}
        selected={selectedYear}
        onSelect={setSelectedYear}
      />
      <SearchFilter
        filterName="Current status"
        items={STATUSES}
        selected={selectedStatus}
        onSelect={setSelectedStatus}
      />
    </aside>
  );
}
