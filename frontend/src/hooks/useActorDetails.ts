import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import ApiClient from '../services/apiClient.ts';

interface ActorDetailsShow {
  id: number;
  image: string;
  title: string;
  status: 'ONGOING' | 'ON_BREAK' | 'ENDED';
  role: string;
}

export interface ActorDetails {
  id: number;
  name: string;
  gender: string;
  birthDate: string;
  biography: string;
  image: string;
  shows: ActorDetailsShow[];
}

const apiClient = new ApiClient('/people');

const useActorDetails = (actorId: number | string) =>
  useQuery({
    queryKey: ['actors', actorId],
    queryFn: () => apiClient.getOne<ActorDetails>(actorId),
    staleTime: ms('10m'),
  });

export default useActorDetails;
