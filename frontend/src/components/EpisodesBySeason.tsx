import { useState } from 'react';
import { IoIosArrowDown, IoIosArrowUp } from 'react-icons/io';
import { Link } from 'react-router';

import type { Season } from '../hooks/useShowDetails.ts';
import Counter from './Counter.tsx';
import EpisodeRow from './EpisodeRow.tsx';
import EpisodeWatchLabel from './EpisodeWatchLabel.tsx';

interface Props {
  isChecked: boolean;
  season: Season;
}

export default function EpisodesBySeason({
  isChecked,
  season,
}: Readonly<Props>) {
  const [open, setOpen] = useState(false);

  return (
    <div>
      <div
        aria-hidden="true"
        onClick={() => setOpen(!open)}
        className="flex w-full cursor-pointer items-center justify-between
          border-b border-b-gray-150 py-4 pr-2"
      >
        <Link
          to="#"
          className="relative cursor-pointer text-lg font-bold text-sky-600
            hover:underline"
          onClick={(e) => e.stopPropagation()}
        >
          Season {season.number}
          <Counter value={season.episodes.length} />
        </Link>
        <div className="flex items-center gap-5">
          {open ? (
            <IoIosArrowUp size={22} className="text-gray-300" />
          ) : (
            <IoIosArrowDown size={22} className="text-gray-300" />
          )}
          <button onClick={(e) => e.stopPropagation()}>
            <EpisodeWatchLabel isChecked={isChecked} />
          </button>
        </div>
      </div>

      <div
        className={`grid transition-all duration-500 ease-in-out
          ${open ? 'grid-rows-[1fr] opacity-100' : 'grid-rows-[0fr] opacity-0'}`}
      >
        <div className="overflow-hidden">
          <ul className="[&>:nth-child(even)]:bg-neutral-100">
            {season.episodes.map((episode) => (
              <EpisodeRow
                episode={episode}
                isChecked={false}
                key={episode.id}
              />
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}
