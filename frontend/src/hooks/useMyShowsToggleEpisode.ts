import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

import type { MyShows } from './useMyShows';

interface ToggleWatchedPayload {
  showId: number | string;
  episodeIds: number[];
}

export const useMyShowsToggleEpisode = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({ episodeIds }: ToggleWatchedPayload) => {
      return axios.post('/api/me/episodes', episodeIds, {
        headers: { 'Content-Type': 'application/json' },
      });
    },

    onMutate: async (payload: ToggleWatchedPayload) => {
      const queryKey = ['my-shows'];
      await queryClient.cancelQueries({ queryKey });

      const previous = queryClient.getQueryData<MyShows>(queryKey);

      // await new Promise(resolve => setTimeout(resolve, 3000));

      queryClient.setQueryData<MyShows>(queryKey, (old) => {
        if (!old) return old;

        return {
          ...old,
          watching: old.watching.map((show) => {
            if (String(show.id) !== String(payload.showId)) {
              return show;
            }

            return {
              ...show,
              seasons: show.seasons.map((season) => ({
                ...season,
                episodes: season.episodes.filter(
                  (ep) => !payload.episodeIds.includes(ep.id),
                ),
              })),
            };
          }),
        };
      });

      return { previous };
    },

    onError: (_err, _payload, context) => {
      if (context?.previous) {
        queryClient.setQueryData(['my-shows'], context.previous);
      }
    },

    onSettled: (_data, _error, { showId, episodeIds }) => {
      void queryClient.invalidateQueries({ queryKey: ['my-shows'] });
      void queryClient.invalidateQueries({ queryKey: ['show', showId] });
      episodeIds.forEach((episodeId) => {
        void queryClient.invalidateQueries({
          queryKey: ['episodes', `${showId}/episodes/${episodeId}`],
        });
      });
    },
  });
};
