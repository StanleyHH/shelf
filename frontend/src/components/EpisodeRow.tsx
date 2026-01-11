import { Rating, ThinStar } from '@smastrom/react-rating';
import { MdModeComment } from 'react-icons/md';
import { Link } from 'react-router';

import { useAuthStore } from '../authStore.ts';
import { type Episode, type ShowDetails } from '../hooks/useShowDetails.ts';
import { useToggleUserEpisodes } from '../hooks/useShowToggleUserEpisode.ts';
import { useShowUpdateUserEpisodeRating } from '../hooks/useShowUpdateUserEpisodeRating.ts';
import EpisodeWatchLabel from './EpisodeWatchLabel.tsx';

interface Props {
  isChecked: boolean;
  episode: Episode;
  show: ShowDetails;
}

const ratingStyle = {
  itemShapes: ThinStar,
  activeFillColor: '#c10007',
  inactiveFillColor: '#cccccc',
};

export default function EpisodeRow({
  isChecked,
  episode,
  show,
}: Readonly<Props>) {
  const { user } = useAuthStore();

  const toggleUserEpisodesMutation = useToggleUserEpisodes();
  const updateRatingMutation = useShowUpdateUserEpisodeRating();
  const isAuthenticated = !!user;

  const handleUserEpisodeStatusUpdate = () => {
    if (!isAuthenticated) return;

    toggleUserEpisodesMutation.mutate({
      showId: String(show.id),
      episodeIds: [episode.id],
      isChecked,
    });
  };

  const handleRatingUpdate = (newRating: number) => {
    if (!isAuthenticated) return;

    const currentEpisode = show.userData?.watchedEpisodes.find(
      (e) => e.id === episode.id,
    );
    const currentRating = currentEpisode?.rating ?? 0;

    if (currentRating > 0 && newRating === 0) {
      return;
    }

    if (newRating === currentRating) {
      return;
    }

    updateRatingMutation.mutate({
      showId: String(show.id),
      episodeId: episode.id,
      rating: newRating,
    });
  };

  return (
    <li
      className="flex items-center justify-between border-b border-b-gray-150
        p-2"
    >
      <div className="flex items-center justify-between gap-5">
        <div className="text-xs font-bold">{episode.number}</div>
        <Link
          to={'/shows/' + show.id + '/episodes/' + episode.id}
          className={`cursor-pointer text-base hover:underline
            ${isChecked ? 'text-neutral-400' : 'text-sky-600'}`}
        >
          {episode.title}
        </Link>
      </div>
      <div className="flex items-center gap-3">
        <div className="text-sm text-neutral-400">{episode.releaseDate}</div>
        <div className="relative inline-flex items-center justify-center">
          <MdModeComment size={23} className="text-neutral-400" />

          <div
            className="absolute flex -translate-y-0.5 items-center
              justify-center text-[10px] text-white"
          >
            0
          </div>
        </div>

        <Rating
          style={{ maxWidth: 110 }}
          onChange={handleRatingUpdate}
          value={
            show.userData?.watchedEpisodes.find((e) => e.id === episode.id)
              ?.rating ?? 0
          }
          itemStyles={ratingStyle}
        />
        <EpisodeWatchLabel
          isChecked={isChecked}
          onClick={handleUserEpisodeStatusUpdate}
        />
      </div>
    </li>
  );
}
