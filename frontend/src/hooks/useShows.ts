import { useInfiniteQuery } from '@tanstack/react-query';

import type Show from '../entities/Show.ts';
import ApiClient, { type FetchResponse } from '../services/apiClient.ts';

const apiClient = new ApiClient<Show>('/shows');

const useShows = () => {
  return useInfiniteQuery<FetchResponse<Show>>({
    queryKey: ['shows'],
    initialPageParam: 1,
    queryFn: ({ pageParam }) =>
      apiClient.getAll({ params: { page: pageParam } }),
    getNextPageParam: (lastPage) => {
      const next = lastPage.page + 1;
      return next <= lastPage.totalPages ? next : undefined;
    },
  });
};

export default useShows;
