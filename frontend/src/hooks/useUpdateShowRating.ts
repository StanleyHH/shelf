import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { ShowDetails } from './useShowDetails';

export const useUpdateShowRating = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      showId,
      rating,
    }: {
      showId: string;
      rating: number;
    }) => {
      return axios.patch(`/api/shows/${showId}/rating`, rating, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, rating }) => {
      const queryKey = ['show', showId];
      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<ShowDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: ShowDetails) => {
        if (!old?.userData) return old;
        return {
          ...old,
          userData: {
            ...old.userData,
            rating,
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
