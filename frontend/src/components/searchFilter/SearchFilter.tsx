import { useRef, useState } from 'react';

import useGenres from '../../hooks/useGenres.ts';
import useShowQueryStore from '../../store.ts';
import SearchFilterList from './SearchFilterList.tsx';
import SearchFilterToggleButton from './SearchFilterToggleButton.tsx';
import SearchFilterInput from './SearchFitlerInput.tsx';

export default function SearchFilter() {
  const { data: items = [] } = useGenres();
  const [query, setQuery] = useState('');
  const [viewAllItems, setViewAllItems] = useState(false);

  const inputRef = useRef<HTMLInputElement>(null);
  const listRef = useRef<HTMLDivElement>(null);

  const setSelectedGenreName = useShowQueryStore((s) => s.setGenreName);
  const selectedGenreName = useShowQueryStore((s) => s.showQuery.genreName);

  const filtered = items.filter((item) =>
    item.name.toLowerCase().includes(query.toLowerCase()),
  );

  function toggleViewAllItems() {
    const newViewAllItems = !viewAllItems;
    setViewAllItems(newViewAllItems);

    if (!newViewAllItems && listRef.current) {
      listRef.current.scrollTop = 0;
    }

    setTimeout(() => {
      inputRef.current?.focus();
    }, 0);
  }

  function getToggleButtonTitle() {
    const hide = filtered.length > 5 ? 'Hide' : '';
    return viewAllItems ? hide : 'View All';
  }

  return (
    <>
      <div className="text-lg font-bold">Item</div>
      <SearchFilterInput
        viewAllItems={viewAllItems}
        query={query}
        setQuery={setQuery}
        ref={inputRef}
      />

      <SearchFilterList
        ref={listRef}
        viewAllItems={viewAllItems}
        filtered={filtered}
        selectedGenreName={selectedGenreName}
        setSelectedGenreName={setSelectedGenreName}
      />

      <SearchFilterToggleButton
        toggleViewAllItems={toggleViewAllItems}
        getToggleButtonTitle={getToggleButtonTitle}
      />
    </>
  );
}
