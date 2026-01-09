import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { ShowDetails } from './useShowDetails';

interface Params {
  showId: string;
  episodeIds: number[];
  isChecked: boolean;
}

export const useToggleUserEpisodes = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ episodeIds, isChecked }: Params) => {
      if (isChecked) {
        return axios.delete('/api/me/episodes', {
          data: episodeIds,
          headers: { 'Content-Type': 'application/json' },
        });
      }

      return axios.post('/api/me/episodes', episodeIds, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, episodeIds, isChecked }) => {
      const queryKey = ['show', showId];
      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<ShowDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: ShowDetails) => {
        if (!old?.userData) return old;

        const currentIds = old.userData.watchedEpisodes ?? [];

        return {
          ...old,
          userData: {
            ...old.userData,
            episodeIds: isChecked
              ? currentIds.filter((id) => !episodeIds.includes(id))
              : [...new Set([...currentIds, ...episodeIds])],
          },
        };
      });

      return { previousShow };
    },

    onError: (_err, { showId }, context) => {
      if (context?.previousShow) {
        queryClient.setQueryData(['show', showId], context.previousShow);
      }
    },

    onSettled: (_data, _error, { showId, episodeIds }) => {
      void queryClient.invalidateQueries({ queryKey: ['show', showId] });
      episodeIds.forEach((episodeId) => {
        void queryClient.invalidateQueries({
          queryKey: ['episodes', showId + '/episodes/' + episodeId],
        });
      });
    },
  });
};
