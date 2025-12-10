import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import type Genre from '../entities/Genre.ts';
import ApiClient from '../services/apiClient.ts';

const apiClient = new ApiClient<Genre>('/genres');

const useGenres = () =>
  useQuery({
    queryKey: ['genres'],
    queryFn: apiClient.getAll,
    staleTime: ms('10m'),
  });

export default useGenres;
