import { Rating, ThinStar } from '@smastrom/react-rating';
import { useParams } from 'react-router';

import yourAd from '../assets/your_ad.jpg';
import ActorCard from '../components/ActorCard.tsx';
import Breadcrumb from '../components/Breadcrumb.tsx';
import Counter from '../components/Counter.tsx';
import EpisodesBySeason from '../components/EpisodesBySeason.tsx';
import FilterLink from '../components/FilterLink.tsx';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';
import ShowStatusBar from '../components/ShowStatusBar.tsx';
import ShowStatusLabel from '../components/ShowStatusLabel.tsx';
import useShowDetails from '../hooks/useShowDetails.ts';
import useShowQueryStore from '../store.ts';

const ratingStyle = {
  itemShapes: ThinStar,
  activeFillColor: '#c10007',
  inactiveFillColor: '#cccccc',
};

export default function ShowDetailsPage() {
  const setCountry = useShowQueryStore((s) => s.setCountryName);
  const setGenre = useShowQueryStore((s) => s.setGenreName);

  const { id } = useParams();
  const { data: show, isLoading, error } = useShowDetails(id!);

  if (isLoading) return '';

  if (error || !show) throw error;

  return (
    <>
      <Breadcrumb currentPage="showDetails" />

      <div className="flex items-center gap-1">
        <p className="mt-2 text-3xl">{show.title}</p>
        <ShowStatusLabel status={show.status} />
      </div>

      <img className="mt-5 w-full" src={show.imageUrl} alt={show.title} />

      <div className="flex justify-between gap-px">
        <ShowStatusBar label="Watching" />
        <ShowStatusBar label="Plan to Watch" />
        <ShowStatusBar label="Dropped" />
        <ShowStatusBar label="Not Watching" isActive={true} />
      </div>

      <div className="mt-5 flex items-center justify-start">
        <div className="flex flex-1 items-center gap-10">
          <div className="text-xl font-bold">My Rating</div>
          <Rating
            style={{ maxWidth: 120 }}
            value={0}
            itemStyles={ratingStyle}
          />
        </div>

        <div className="relative flex-1 text-2xl">
          4.26
          <Counter value="23" />
        </div>
      </div>

      <div className="mt-10">
        <div className="flex">
          <div className="flex-1 text-neutral-400">Original Air Dates:</div>
          <div className="flex-1">
            {show.firstAirDate} — {show.lastAirDate}
          </div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Country:</div>
          <div className="flex-1">
            {show.countries.map((country, index) => (
              <span key={country.id}>
                <FilterLink
                  item={country.name}
                  onClick={setCountry}
                  key={country.id}
                />
                {index < show.countries.length - 1 && ', '}
              </span>
            ))}
          </div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Genre:</div>
          <div className="flex-1">
            {show.genres.map((genre, index) => (
              <span key={genre.id}>
                <FilterLink item={genre.name} onClick={setGenre} />
                {index < show.genres.length - 1 && ', '}
              </span>
            ))}
          </div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Network:</div>
          <div className="flex-1">{show.network}</div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Watched by:</div>
          <div className="relative flex-1">
            44
            <Counter value="122" />
          </div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Total running time:</div>
          <div className="flex-1">1 day 14 minutes</div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Episode duration:</div>
          <div className="flex-1">25 min.</div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Episodes:</div>
          <div className="flex-1">59</div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">IMDB Rating:</div>
          <div className="relative flex-1">
            {show.imdbRating} of 10
            <Counter value="34 909" />
          </div>
        </div>
      </div>

      <div className="relative mt-5 text-xl font-bold">
        Cast
        <Counter value={show.actors.length.toString()} />
      </div>

      <div className="mt-3 grid grid-cols-2 gap-y-5">
        {show.actors.map((actor) => (
          <ActorCard
            name={actor.name}
            role={actor.role}
            image={actor.image}
            key={actor.id}
          />
        ))}
      </div>

      <div className="mt-15 text-xl font-bold">Episode Guide</div>
      {show.seasons.map((season) => (
        <EpisodesBySeason season={season} isChecked={false} key={season.id} />
      ))}

      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
