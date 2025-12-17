import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import type Genre from '../entities/Genre.ts';
import ApiClient from '../services/apiClient.ts';

const apiClient = new ApiClient('/genres');

const useGenres = () =>
  useQuery({
    queryKey: ['genres'],
    queryFn: apiClient.getList<Genre>,
    staleTime: ms('10m'),
  });

export default useGenres;
