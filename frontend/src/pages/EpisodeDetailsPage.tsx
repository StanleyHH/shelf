import { Rating, ThinStar } from '@smastrom/react-rating';
import { useParams } from 'react-router';

import Breadcrumb from '../components/Breadcrumb.tsx';
import Counter from '../components/Counter.tsx';
import EpisodeWatchLabel from '../components/EpisodeWatchLabel.tsx';
import InfoRow from '../components/InfoRow.tsx';
import useEpisodeDetails from '../hooks/useEpisodesDetails.ts';

const ratingStyle = {
  itemShapes: ThinStar,
  activeFillColor: '#c10007',
  inactiveFillColor: '#cccccc',
};

export default function EpisodeDetailsPage() {
  const { showId, episodeId } = useParams();
  const {
    data: episode,
    isLoading,
    error,
  } = useEpisodeDetails(showId + '/episodes/' + episodeId);

  if (isLoading) return '';

  if (error || !episode) throw error;

  return (
    <>
      <Breadcrumb
        navLinks={[
          { label: 'Home', to: '/' },
          { label: 'Shows', to: '/shows' },
          { label: episode.show.title, to: '/shows/' + showId },
          { label: episode.title },
        ]}
      />

      <p className="mt-2 text-2xl">
        s{String(episode.seasonNumber).padStart(2, '0')}e
        {String(episode.episodeNumber).padStart(2, '0')} - {episode.title}
      </p>

      <img src={episode.image} alt={episode.image} className="mt-3 w-full" />

      <div className="flex items-center border-b border-b-gray-200 py-5">
        <div className="flex flex-1 items-center gap-2">
          <EpisodeWatchLabel isChecked={false} />
          <Rating
            style={{ maxWidth: 110 }}
            value={0}
            itemStyles={ratingStyle}
          />
        </div>
        <div className="relative flex-1 text-2xl">
          {episode.averageRating.toFixed(2)}
          <span className="absolute">
            <Counter value={episode.averageRatingVotesCount} />
          </span>
        </div>
      </div>

      <div className="border-b border-b-gray-150 py-5">
        <InfoRow title="Runtime">{episode.runtime} min.</InfoRow>
        <InfoRow title="Release Date">{episode.releaseDate.toString()}</InfoRow>
        <InfoRow title="Watched by:">
          <div className="relative">
            {episode.watchedBy}
            <span>
              <Counter value={episode.watchedByPercent} />
            </span>
          </div>
        </InfoRow>
      </div>
    </>
  );
}
