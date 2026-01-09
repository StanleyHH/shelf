import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { ShowDetails } from './useShowDetails';

interface Params {
  showId: string;
  episodeId: number;
  rating: number;
}

export const useShowUpdateUserEpisodeRating = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ episodeId, rating }: Params) => {
      return axios.patch(`/api/me/episodes/${episodeId}/rating`, rating, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, episodeId, rating }: Params) => {
      const queryKey = ['show', showId];
      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<ShowDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: ShowDetails) => {
        if (!old?.userData?.watchedEpisodes) return old;

        const updatedWatched = old.userData.watchedEpisodes.map((ep) =>
          ep.id === episodeId ? { ...ep, rating } : ep,
        );

        return {
          ...old,
          userData: {
            ...old.userData,
            watchedEpisodes: updatedWatched,
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

    onSettled: (_data, _error, { showId, episodeId }) => {
      void queryClient.invalidateQueries({ queryKey: ['show', showId] });
      void queryClient.invalidateQueries({
        queryKey: ['episodes', `${showId}/episodes/${episodeId}`],
      });
    },
  });
};
