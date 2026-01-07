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

export interface Actor {
  id: number;
  name: string;
  image: string;
  role: string;
}

export type UserShowStatus =
  | 'WATCHING'
  | 'PLAN_TO_WATCH'
  | 'DROPPED'
  | 'NOT_WATCHING';

interface UserData {
  status: UserShowStatus;
  rating: number;
  watchedEpisodes: number[];
}

export interface ShowDetails {
  id: number;
  title: string;
  status: 'ONGOING' | 'ON_BREAK' | 'ENDED';
  firstAirDate: string;
  lastAirDate: string;
  imageUrl: string;
  network: string;
  imdbRating: number;
  imdbVotesCount: number;
  description: string;
  averageRating: number;
  averageRatingVotesCount: number;
  watchedBy: number;
  usersTotal: number;
  averageEpisodeRuntime: number;
  totalRuntime: number;
  countries: Country[];
  genres: Genre[];
  seasons: Season[];
  actors: Actor[];
  userData: UserData;
}

const apiClient = new ApiClient('/shows');

const useShowDetails = (showId: number | string) =>
  useQuery({
    queryKey: ['show', String(showId)],
    queryFn: () => apiClient.getOne<ShowDetails>(showId),
    staleTime: ms('10m'),
  });

export default useShowDetails;
