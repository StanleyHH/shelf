import { useState } from 'react';
import { IoIosArrowDown, IoIosArrowUp } from 'react-icons/io';

import { useAuthStore } from '../authStore.ts';
import { type Season, type ShowDetails } from '../hooks/useShowDetails.ts';
import { useToggleUserEpisodes } from '../hooks/useShowToggleUserEpisode.ts';
import Counter from './Counter.tsx';
import EpisodeRow from './EpisodeRow.tsx';
import EpisodeWatchLabel from './EpisodeWatchLabel.tsx';

interface Props {
  isChecked: boolean;
  season: Season;
  show: ShowDetails;
}

export default function EpisodesBySeason({
  isChecked,
  season,
  show,
}: Readonly<Props>) {
  const [open, setOpen] = useState(false);
  const { user } = useAuthStore();
  const toggleUserEpisodesMutation = useToggleUserEpisodes();
  const isAuthenticated = !!user;

  const handleUserEpisodeStatusUpdate = (e: {
    stopPropagation: () => void;
  }) => {
    e.stopPropagation();
    if (!isAuthenticated) return;

    toggleUserEpisodesMutation.mutate({
      showId: String(show.id),
      episodeIds: season.episodes.map((episode) => episode.id),
      isChecked,
    });
  };

  return (
    <div>
      <div
        aria-hidden="true"
        onClick={() => setOpen(!open)}
        className="flex w-full cursor-pointer items-center justify-between
          border-b border-b-gray-150 py-4 pr-2"
      >
        <div className="relative text-lg font-bold">
          Season {season.number}
          <Counter value={season.episodes.length} />
        </div>
        <div className="flex items-center gap-5">
          {open ? (
            <IoIosArrowUp size={22} className="text-gray-300" />
          ) : (
            <IoIosArrowDown size={22} className="text-gray-300" />
          )}
          <EpisodeWatchLabel
            isChecked={isChecked}
            onClick={handleUserEpisodeStatusUpdate}
          />
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
                isChecked={
                  show?.userData?.watchedEpisodes.some(
                    (we) => we.id === episode.id,
                  ) ?? false
                }
                key={episode.id}
                show={show}
              />
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}
