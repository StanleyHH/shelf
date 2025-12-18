import { Link, useParams } from 'react-router';

import yourAd from '../assets/your_ad.jpg';
import Breadcrumb from '../components/Breadcrumb.tsx';
import Counter from '../components/Counter.tsx';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';
import ShowStatusLabel from '../components/ShowStatusLabel.tsx';
import useActorDetails from '../hooks/useActorDetails.ts';

export default function ActorDetailsPage() {
  const { id } = useParams();
  const { data: actor, isLoading, error } = useActorDetails(id!);

  if (isLoading) return '';

  if (error || !actor) throw error;

  return (
    <>
      <Breadcrumb
        navLinks={[{ label: 'Home', to: '/' }, { label: actor.name }]}
      />

      <p className="mt-2 text-2xl">{actor.name}</p>

      <div className="mt-5 flex items-start gap-8">
        <div className="w-60">
          <img src={actor.image} alt={actor.name} className="w-full rounded" />
        </div>

        <div className="flex-1">
          <div className="grid grid-cols-2">
            <div className="text-neutral-400">Name:</div>
            <div>{actor.name}</div>
          </div>
          <div className="grid grid-cols-2">
            <div className="text-neutral-400">Gender:</div>
            <div>{actor.gender.toLowerCase()}</div>
          </div>
          <div className="grid grid-cols-2">
            <div className="text-neutral-400">Date of Birth:</div>
            <div>{actor.birthDate}</div>
          </div>
        </div>
      </div>

      <div className="relative mt-5 text-xl font-bold">Biography</div>
      <div className="mt-3 leading-5">{actor.biography}</div>

      <div className="relative mt-5 text-xl font-bold">
        Shows
        <Counter value={actor.shows.length} />
      </div>

      <div className="mt-3 grid grid-cols-3 gap-5">
        {actor.shows.map((show) => (
          <Link
            key={show.id}
            to={'/shows/' + show.id}
            className="flex flex-col gap-1 transition-transform duration-150
              ease-in-out hover:scale-[1.03]"
          >
            <img className="object-contain" src={show.image} alt={show.title} />
            <div className="flex items-start justify-between">
              <p className="text-base/5 font-bold text-sky-600">{show.title}</p>
              <ShowStatusLabel status={show.status} />
            </div>
            <p className="text-sm/4">{show.role}</p>
          </Link>
        ))}
      </div>

      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
