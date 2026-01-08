import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { ShowDetails, UserShowStatus } from './useShowDetails';

export const useUpdateShowStatus = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      showId,
      status,
    }: {
      showId: string;
      status: UserShowStatus;
    }) => {
      return axios.put(`/api/shows/${showId}/status`, status, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, status }) => {
      const queryKey = ['show', showId];
      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<ShowDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: ShowDetails) => {
        if (!old?.userData) return old;
        return {
          ...old,
          userData: {
            ...old.userData,
            status,
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
