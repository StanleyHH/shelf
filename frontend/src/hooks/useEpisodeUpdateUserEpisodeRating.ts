import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { EpisodeDetails } from './useEpisodesDetails.ts';

interface Params {
  showId: string;
  episodeId: number;
  rating: number;
}

export const useEpisodeUpdateUserEpisodeRating = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ episodeId, rating }: Params) => {
      return axios.patch(`/api/me/episodes/${episodeId}/rating`, rating, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, episodeId, rating }: Params) => {
      const queryKey = ['episodes', showId + '/episodes/' + episodeId];
      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<EpisodeDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: EpisodeDetails) => {
        if (!old?.userData?.rating) return old;

        return {
          ...old,
          userData: {
            ...old.userData,
            rating: rating,
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
