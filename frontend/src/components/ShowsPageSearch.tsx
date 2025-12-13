import { useRef } from 'react';

import useShowQueryStore from '../store';

export default function ShowsPageSearch() {
  const searchText = useShowQueryStore((s) => s.showQuery.searchText);
  const setSearchText = useShowQueryStore((s) => s.setSearchText);

  const inputRef = useRef<HTMLInputElement | null>(null);

  const submit = () => {
    if (inputRef.current) {
      setSearchText(inputRef.current.value);
    }
  };

  return (
    <div className="mt-5 flex overflow-hidden rounded-sm border">
      <input
        ref={inputRef}
        placeholder="Rick and Morty"
        defaultValue={searchText}
        className="w-full border-0 px-4 focus:outline-hidden"
        onKeyDown={(e) => {
          if (e.key === 'Enter') {
            submit();
          }
        }}
      />
      <button
        onClick={submit}
        className="cursor-pointer bg-red-600 px-8 py-2 text-lg font-bold
          text-white"
      >
        Search
      </button>
    </div>
  );
}
