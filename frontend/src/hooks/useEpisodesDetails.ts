import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import type Show from '../entities/Show.ts';
import ApiClient from '../services/apiClient.ts';

interface UserData {
  rating: number;
  watched: boolean;
}

export interface EpisodeDetails {
  id: number;
  episodeNumber: number;
  seasonNumber: number;
  title: string;
  releaseDate: string;
  runtime: number;
  image: string;
  averageRating: number;
  averageRatingVotesCount: number;
  watchedBy: number;
  watchedByPercent: string;
  show: Show;
  userData: UserData;
}

const apiClient = new ApiClient('/shows');

const useEpisodeDetails = (path: string) =>
  useQuery({
    queryKey: ['episodes', path],
    queryFn: () => apiClient.getOne<EpisodeDetails>(path),
    staleTime: ms('10m'),
  });

export default useEpisodeDetails;
