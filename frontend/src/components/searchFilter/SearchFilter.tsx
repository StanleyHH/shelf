import { useRef, useState } from 'react';

import type Item from '../../entities/Item.ts';
import SearchFilterList from './SearchFilterList.tsx';
import SearchFilterToggleButton from './SearchFilterToggleButton.tsx';
import SearchFilterInput from './SearchFitlerInput.tsx';

interface Props {
  filterName: string;
  items: Item[];
  selected?: string;
  onSelect: (value: string) => void;
}

export default function SearchFilter({
  filterName,
  items,
  selected,
  onSelect,
}: Readonly<Props>) {
  const [query, setQuery] = useState('');
  const [viewAllItems, setViewAllItems] = useState(false);

  const inputRef = useRef<HTMLInputElement>(null);
  const listRef = useRef<HTMLDivElement>(null);

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

  function getToggleButtonLabel() {
    const hide = filtered.length > 5 ? 'Hide' : '';
    return viewAllItems ? hide : 'View All';
  }

  return (
    <>
      <div className="mt-5 text-lg font-bold">{filterName}</div>
      <SearchFilterInput
        viewAllItems={viewAllItems}
        query={query}
        onChange={setQuery}
        ref={inputRef}
      />

      <SearchFilterList
        ref={listRef}
        viewAllItems={viewAllItems}
        filtered={filtered}
        selected={selected}
        onClick={onSelect}
      />

      <SearchFilterToggleButton
        onClick={toggleViewAllItems}
        label={getToggleButtonLabel()}
      />
    </>
  );
}
