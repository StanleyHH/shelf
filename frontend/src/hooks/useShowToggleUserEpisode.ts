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

    onMutate: async ({ showId, episodeIds, isChecked }: Params) => {
      const queryKey = ['show', showId];

      await queryClient.cancelQueries({ queryKey });

      const previousShow = queryClient.getQueryData<ShowDetails>(queryKey);

      queryClient.setQueryData<ShowDetails>(queryKey, (old?: ShowDetails) => {
        if (!old?.userData) return old;

        const current = old.userData.watchedEpisodes ?? [];

        let newWatched: typeof current;

        if (isChecked) {
          newWatched = current.filter((ep) => !episodeIds.includes(ep.id));
        } else {
          const existingIds = new Set(current.map((ep) => ep.id));

          const toAdd = episodeIds
            .filter((id) => !existingIds.has(id))
            .map((id) => ({
              id,
              rating: 0,
            }));

          newWatched = [...current, ...toAdd];
        }

        return {
          ...old,
          userData: {
            ...old.userData,
            watchedEpisodes: newWatched,
          },
        };
      });

      return { previousShow };
    },

    onError: (
      _err: Error,
      { showId }: Params,
      previousShowContext?: { previousShow?: ShowDetails },
    ) => {
      if (previousShowContext?.previousShow) {
        queryClient.setQueryData(
          ['show', showId],
          previousShowContext.previousShow,
        );
      }
    },

    onSettled: (_data, _error, { showId, episodeIds }) => {
      void queryClient.invalidateQueries({ queryKey: ['show', showId] });

      episodeIds.forEach((episodeId) => {
        void queryClient.invalidateQueries({
          queryKey: ['episodes', `${showId}/episodes/${episodeId}`],
        });
      });
    },
  });
};
