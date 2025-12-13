import type { Ref } from 'react';

import type Item from '../../entities/Item.ts';

interface Props {
  ref?: Ref<HTMLDivElement>;
  viewAllItems: boolean;
  filtered: Item[];
  selected?: string;
  onClick: (name: string) => void;
}

const getCriteria = (item: Item) => {
  return item.type === 'status' ? item.id : item.name;
};

export default function SearchFilterList({
  ref,
  viewAllItems,
  filtered,
  onClick,
  selected,
}: Readonly<Props>) {
  return (
    <div
      ref={ref}
      className={`mt-1.5 max-h-43
        ${viewAllItems ? 'overflow-y-auto' : 'overflow-y-hidden'}`}
    >
      <ul>
        {filtered.length === 0 ? (
          <li>Nothing found</li>
        ) : (
          filtered.map((item) => (
            <li key={item.id}>
              <button
                className={`hover:cursor-pointer hover:underline ${
                  selected === getCriteria(item)
                    ? 'text-neutral-400'
                    : 'text-sky-600'
                  }`}
                onClick={() => onClick(getCriteria(item))}
              >
                {item.name}
              </button>
            </li>
          ))
        )}
      </ul>
    </div>
  );
}
