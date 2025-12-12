import type { Ref } from 'react';
import { IoCloseSharp } from 'react-icons/io5';

interface Props {
  viewAllItems: boolean;
  query: string;
  setQuery: (query: string) => void;
  ref?: Ref<HTMLInputElement>;
}

export default function SearchFilterInput({
  viewAllItems,
  query,
  setQuery,
  ref,
}: Readonly<Props>) {
  return (
    <div className={viewAllItems ? 'relative mt-2 flex' : 'hidden'}>
      <input
        value={query}
        onChange={(event) => setQuery(event.target.value)}
        ref={ref}
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
  );
}
