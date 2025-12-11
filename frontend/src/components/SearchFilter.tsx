import { useRef, useState } from 'react';
import { IoCloseSharp } from 'react-icons/io5';

import useGenres from '../hooks/useGenres.ts';
import useShowQueryStore from '../store.ts';

export default function SearchFilter() {
  const { data: items = [] } = useGenres();
  const [query, setQuery] = useState('');
  const [viewAll, setViewAll] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const listRef = useRef<HTMLDivElement>(null);

  const setSelectedGenreId = useShowQueryStore((s) => s.setGenreId);
  const selectedGenreName = useShowQueryStore((s) => s.showQuery.genreName);

  const filtered = items.filter((item) =>
    item.name.toLowerCase().includes(query.toLowerCase()),
  );

  function toggle() {
    const newViewAll = !viewAll;
    setViewAll(newViewAll);

    if (!newViewAll && listRef.current) {
      listRef.current.scrollTop = 0;
    }

    setTimeout(() => {
      inputRef.current?.focus();
    }, 0);
  }

  function getHideStatus() {
    const hide = filtered.length > 5 ? 'Hide' : '';
    return viewAll ? hide : 'View All';
  }

  return (
    <>
      <div className="text-lg font-bold">Item {items.length}</div>
      <div className={viewAll ? 'relative mt-2 flex' : 'hidden'}>
        <input
          value={query}
          onChange={(event) => setQuery(event.target.value)}
          ref={inputRef}
          className="w-full rounded-sm border border-gray-300 px-2 py-0.5 pr-7
            focus:border-gray-700 focus:outline-hidden"
        />
        <button
          onClick={() => {
            setQuery('');
          }}
          className="absolute right-1.5 bottom-1.5 text-lg hover:cursor-pointer
            hover:text-blue-500"
        >
          <IoCloseSharp />
        </button>
      </div>
      <div
        ref={listRef}
        className={`mt-1.5 max-h-43
          ${viewAll ? 'overflow-y-auto' : 'overflow-y-hidden'}`}
      >
        <ul>
          {filtered.length === 0 ? (
            <li>Nothing found</li>
          ) : (
            filtered.map((item) => (
              <li key={item.id}>
                <button
                  className={`hover:cursor-pointer hover:underline ${
                    selectedGenreName === item.name
                      ? 'text-neutral-400'
                      : 'text-sky-600'
                    }`}
                  onClick={() => setSelectedGenreId(item.name)}
                >
                  {item.name}
                </button>
              </li>
            ))
          )}
        </ul>
      </div>
      <button
        className="cursor-pointer text-red-500 hover:underline"
        onClick={toggle}
      >
        {getHideStatus()}
      </button>
    </>
  );
}
