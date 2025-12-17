import { useState } from 'react';

import type { Actor } from '../hooks/useShowDetails.ts';
import ActorCard from './ActorCard.tsx';

interface Props {
  actors: Actor[];
}

export function ActorsGrid({ actors }: Readonly<Props>) {
  const [showAll, setShowAll] = useState(false);

  const actorsToShow = showAll ? actors : actors.slice(0, 5);

  return (
    <div className="mt-3 grid grid-cols-2 gap-y-5">
      {actorsToShow.map((actor) => (
        <ActorCard
          key={actor.id}
          name={actor.name}
          role={actor.role}
          image={actor.image}
        />
      ))}

      {!showAll && actors.length > 5 && (
        <button
          onClick={() => setShowAll(true)}
          className="text-sky-600 hover:underline text-start cursor-pointer"
        >
          View All
        </button>
      )}
    </div>
  );
}
