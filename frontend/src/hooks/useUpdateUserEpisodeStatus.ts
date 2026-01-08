import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { ShowDetails } from './useShowDetails.ts';

export const useUpdateUserEpisodeStatus = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      episodeIds,
    }: {
      showId: string;
      episodeIds: number[];
    }) => {
      return axios.post('/api/me/episodes', episodeIds, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, episodeIds }) => {
      const queryKey = ['show', showId];
      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<ShowDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: ShowDetails) => {
        if (!old?.userData) return old;
        return {
          ...old,
          userData: {
            ...old.userData,
            episodeIds,
          },
        };
      });

      return { previousShow };
    },

    onError: (_err, { showId }, context) => {
      const queryKey = ['show', showId];
      if (context?.previousShow) {
        queryClient.setQueryData(queryKey, context.previousShow);
      }
    },

    onSettled: (_data, _error, { showId }) => {
      const queryKey = ['show', String(showId)];
      void queryClient.invalidateQueries({ queryKey });
    },
  });
};
