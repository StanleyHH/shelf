import humanizeDuration from 'humanize-duration';
import { useState } from 'react';
import { Link } from 'react-router';

import yourAd from '../assets/your_ad.jpg';
import Breadcrumb from '../components/Breadcrumb.tsx';
import EpisodesBySeason from '../components/EpisodesBySeason.tsx';
import MyShowSecondaryTitle from '../components/MyShowSecondaryTitle.tsx';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';
import ShowStatusLabel from '../components/ShowStatusLabel.tsx';
import useMyShows from '../hooks/useMyShows.ts';
import { useMyShowsToggleEpisode } from '../hooks/useMyShowsToggleEpisode.ts';

export default function MyShows() {
  const [isWatchingActive, setIsWatchingActive] = useState(true);
  const { data: shows } = useMyShows();
  const toggleUserEpisodesMutation = useMyShowsToggleEpisode();

  return (
    <>
      <Breadcrumb
        navLinks={[{ label: 'Home', to: '/' }, { label: 'My Shows' }]}
      />
      <p className="mt-2 text-2xl">My Shows</p>

      <div className="mt-5 flex gap-7">
        <MyShowSecondaryTitle
          title="Watching"
          quantity={shows?.watching.length ?? 0}
          isActive={isWatchingActive}
          onClick={() => setIsWatchingActive(true)}
        />
        <MyShowSecondaryTitle
          title="Plan to Watch"
          quantity={shows?.planToWatch.length ?? 0}
          isActive={!isWatchingActive}
          onClick={() => setIsWatchingActive(false)}
        />
      </div>

      {isWatchingActive &&
        shows?.watching.map((show) => {
          return (
            <div className="mt-5" key={show.id}>
              <div className="flex justify-between">
                <div className="flex items-center gap-1">
                  <Link
                    className="text-xl font-bold text-sky-600 hover:underline"
                    to={'/shows/' + show.id}
                  >
                    {show.title}
                  </Link>
                  <ShowStatusLabel status={show.status} />
                </div>
                <div className="text-sm">
                  {humanizeDuration(show.totalTime * 60 * 1000, { largest: 3 })}
                </div>
              </div>
              <div className="ml-4">
                {show?.seasons?.map((season) => (
                  <EpisodesBySeason
                    season={season}
                    isChecked={false}
                    key={season.id}
                    show={show}
                    onEpisodeChange={toggleUserEpisodesMutation.mutate}
                  />
                ))}
              </div>
            </div>
          );
        })}
      {!isWatchingActive && <div>Plan to Watch Shows</div>}

      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
