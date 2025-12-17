import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import type Country from '../entities/Country.ts';
import type Genre from '../entities/Genre.ts';
import ApiClient from '../services/apiClient.ts';

export interface Season {
  id: number;
  number: number;
  episodes: Episode[];
}

export interface Episode {
  id: number;
  number: number;
  title: string;
  releaseDate: string;
}

interface Actor {
  id: number;
  name: string;
  image: string;
  role: string;
}

interface ShowDetails {
  id: number;
  title: string;
  status: 'ONGOING' | 'ON_BREAK' | 'ENDED';
  firstAirDate: string;
  lastAirDate: string;
  imageUrl: string;
  network: string;
  imdbRating: number;
  description: string;
  countries: Country[];
  genres: Genre[];
  seasons: Season[];
  actors: Actor[];
}

const apiClient = new ApiClient('/shows');

const useShowDetails = (showId: number | string) =>
  useQuery({
    queryKey: ['show', showId],
    queryFn: () => apiClient.getOne<ShowDetails>(showId),
    staleTime: ms('10m'),
  });

export default useShowDetails;
