import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { EpisodeDetails } from './useEpisodesDetails.ts';

interface Params {
  showId: string;
  episodeId: number;
  isChecked: boolean;
}

export const useToggleUserEpisodes = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ episodeId, isChecked }: Params) => {
      if (isChecked) {
        return axios.delete('/api/me/episodes', {
          data: [episodeId],
          headers: { 'Content-Type': 'application/json' },
        });
      }

      return axios.post('/api/me/episodes', [episodeId], {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async ({ showId, episodeId, isChecked }) => {
      const queryKey = ['episodes', showId + '/episodes/' + episodeId];
      await queryClient.cancelQueries({ queryKey });

      const previousEpisode =
        queryClient.getQueryData<EpisodeDetails>(queryKey);

      queryClient.setQueryData(queryKey, (old?: EpisodeDetails) => {
        if (!old?.userData) return old;

        return {
          ...old,
          userData: {
            ...old.userData,
            watched: !isChecked,
          },
        };
      });

      return { previousEpisode };
    },

    onError: (_err, { showId }, context) => {
      if (context?.previousEpisode) {
        queryClient.setQueryData(['show', showId], context.previousEpisode);
      }
    },

    onSettled: (_data, _error, { showId, episodeId }) => {
      void queryClient.invalidateQueries({ queryKey: ['show', showId] });
      void queryClient.invalidateQueries({
        queryKey: ['episodes', showId + '/episodes/' + episodeId],
      });
    },
  });
};
