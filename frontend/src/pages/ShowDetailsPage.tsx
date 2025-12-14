import { Rating, ThinStar } from '@smastrom/react-rating';

import yourAd from '../assets/your_ad.jpg';
import ActorCard from '../components/ActorCard.tsx';
import Breadcrumb from '../components/Breadcrumb.tsx';
import Counter from '../components/Counter.tsx';
import FilterLink from '../components/FilterLink.tsx';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';
import ShowStatusBar from '../components/ShowStatusBar.tsx';
import ShowStatusLabel from '../components/ShowStatusLabel.tsx';
import useShowQueryStore from '../store.ts';

const ratingStyle = {
  itemShapes: ThinStar,
  activeFillColor: '#c10007',
  inactiveFillColor: '#cccccc',
};

export default function ShowDetailsPage() {
  const setCountry = useShowQueryStore((s) => s.setCountryName);
  const setGenre = useShowQueryStore((s) => s.setGenreName);

  return (
    <>
      <Breadcrumb currentPage="showDetails" />

      <div className="flex items-center gap-1">
        <p className="mt-2 text-3xl">Solar Opposites</p>
        <ShowStatusLabel status="ONGOING" />
      </div>

      <img
        className="mt-5"
        src="https://static.tvmaze.com/uploads/images/original_untouched/272/681219.jpg"
        alt="Solar Opposites background"
      />

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
          <div className="flex-1">08.05.2020 — 13.10.2025</div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Country:</div>
          <div className="flex-1">
            <FilterLink item="US" onClick={setCountry} />
          </div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Genre:</div>
          <div className="flex-1">
            <FilterLink item="Comedy" onClick={setGenre} />, {}
            <FilterLink item="Sci-Fi" onClick={setGenre} />
          </div>
        </div>
        <div className="flex">
          <div className="flex-1 text-neutral-400">Network:</div>
          <div className="flex-1">Hulu</div>
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
            7.9 of 10
            <Counter value="34 909" />
          </div>
        </div>
      </div>

      <div className="relative mt-5 text-xl font-bold">
        Cast
        <Counter value="4" />
      </div>

      <div className="mt-3 grid grid-cols-2 gap-y-5">
        <ActorCard
          name="Dan Stevens"
          role="Korvo (voice)"
          image="https://static.tvmaze.com/uploads/images/medium_untouched/2/7268.jpg"
        />
        <ActorCard
          name="Sean Giambrone"
          role="Yumyulack (voice)"
          image="https://static.tvmaze.com/uploads/images/medium_untouched/2/7194.jpg"
        />
        <ActorCard
          name="Thomas Middleditch"
          role="Terry (voice)"
          image="https://static.tvmaze.com/uploads/images/original_untouched/3/8320.jpg"
        />
        <ActorCard
          name="Mary Mack"
          role="Jesse (voice)"
          image="https://static.tvmaze.com/uploads/images/medium_portrait/149/373001.jpg"
        />
      </div>

      <div className="mt-15 text-xl font-bold">Episode Guide</div>

      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
