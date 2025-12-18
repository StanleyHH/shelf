import { Rating, ThinStar } from '@smastrom/react-rating';
import { MdModeComment } from 'react-icons/md';
import { Link } from 'react-router';

import type { Episode } from '../hooks/useShowDetails.ts';
import EpisodeWatchLabel from './EpisodeWatchLabel.tsx';

interface Props {
  isChecked: boolean;
  episode: Episode;
}

const ratingStyle = {
  itemShapes: ThinStar,
  activeFillColor: '#c10007',
  inactiveFillColor: '#cccccc',
};

export default function EpisodeRow({ isChecked, episode }: Readonly<Props>) {
  return (
    <li
      className="flex items-center justify-between border-b border-b-gray-150
        p-2"
    >
      <div className="flex items-center justify-between gap-5">
        <div className="text-xs font-bold">{episode.number}</div>
        <Link
          to={'episodes/' + episode.id}
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
            23
          </div>
        </div>

        <Rating style={{ maxWidth: 110 }} value={0} itemStyles={ratingStyle} />
        <EpisodeWatchLabel isChecked={isChecked} />
      </div>
    </li>
  );
}
