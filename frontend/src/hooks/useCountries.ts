import { useQuery } from '@tanstack/react-query';
import ms from 'ms';

import type Country from '../entities/Country.ts';
import ApiClient from '../services/apiClient.ts';

const apiClient = new ApiClient('/countries');

const useCountries = () =>
  useQuery({
    queryKey: ['countries'],
    queryFn: apiClient.getList<Country>,
    staleTime: ms('10m'),
  });

export default useCountries;
