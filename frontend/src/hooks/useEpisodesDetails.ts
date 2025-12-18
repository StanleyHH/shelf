import ApiClient from '../services/apiClient.ts';
import { useQuery } from '@tanstack/react-query';
import ms from 'ms';
import type Show from '../entities/Show.ts';

interface EpisodeDetails {
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
}

const apiClient = new ApiClient('/shows');

const useEpisodeDetails = (path: string) =>
  useQuery({
    queryKey: ['episodes', path],
    queryFn: () => apiClient.getOne<EpisodeDetails>(path),
    staleTime: ms('10m')
  });

export default useEpisodeDetails;