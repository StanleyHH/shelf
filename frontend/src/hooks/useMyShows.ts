import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import ApiClient from '../services/apiClient.ts';
import type { Season } from './useShowDetails.ts';

export interface MyShowsWatching {
  id: number;
  title: string;
  status: 'ONGOING' | 'ON_BREAK' | 'ENDED';
  totalTime: number;
  seasons: Season[];
}

interface MyShowsPlanToWatch {
  id: number;
  title: string;
  status: 'ONGOING' | 'ON_BREAK' | 'ENDED';
  totalSeasons: number;
}

interface MyShows {
  watching: MyShowsWatching[];
  planToWatch: MyShowsPlanToWatch[];
}

const apiClient = new ApiClient('/me/my-shows');

const useMyShows = () =>
  useQuery({
    queryKey: ['my-shows'],
    queryFn: () => apiClient.getMyShows<MyShows>(),
    staleTime: ms('10m'),
  });

export default useMyShows;
