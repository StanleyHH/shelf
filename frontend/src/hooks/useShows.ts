import { useInfiniteQuery } from '@tanstack/react-query';
import ms from 'ms';

import type Show from '../entities/Show.ts';
import ApiClient, { type FetchResponse } from '../services/apiClient.ts';
import useShowQueryStore from '../store.ts';

const apiClient = new ApiClient('/shows');

const useShows = () => {
  const showQuery = useShowQueryStore((s) => s.showQuery);

  return useInfiniteQuery<FetchResponse<Show>, Error>({
    queryKey: ['shows', showQuery],
    initialPageParam: 1,
    queryFn: ({ pageParam }) =>
      apiClient.getAll<Show>({
        params: {
          q: showQuery.searchText,
          genre: showQuery.genreName,
          country: showQuery.countryName,
          status: showQuery.status,
          year: showQuery.year,
          page: pageParam,
        },
      }),
    getNextPageParam: (lastPage) => {
      const next = lastPage.page + 1;
      return next <= lastPage.totalPages ? next : undefined;
    },
    staleTime: ms('10m'),
  });
};

export default useShows;
