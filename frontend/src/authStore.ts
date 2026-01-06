import axios, { isAxiosError } from 'axios';
import { create } from 'zustand';

export interface GithubUserAttributes {
  login: string;
  avatar_url: string;
}

export interface AuthUser {
  attributes: GithubUserAttributes;
}

interface AuthState {
  user: AuthUser | null;
  isLoading: boolean;
  error: string | null;
  fetchUser: () => Promise<void>;
  logout: () => Promise<void>;
}

export const useAuthStore = create<AuthState>()((set) => ({
  user: null,
  isLoading: true,
  error: null,

  fetchUser: async () => {
    set({ isLoading: true, error: null });

    try {
      const res = await axios.get<AuthUser>('/api/auth', {
        withCredentials: true,
      });
      set({ user: res.data, isLoading: false });
    } catch (err: unknown) {
      let errorMessage = 'Не удалось загрузить пользователя';

      if (isAxiosError(err)) {
        errorMessage =
          err.response?.data?.message ?? err.message ?? 'Request error';
      } else if (err instanceof Error) {
        errorMessage = err.message;
      }

      set({
        user: null,
        isLoading: false,
        error: errorMessage,
      });
    }
  },

  logout: async () => {
    set({ isLoading: true, error: null });

    try {
      await axios.post('/logout', {}, { withCredentials: true });
      set({ user: null, isLoading: false });
    } catch (err: unknown) {
      let errorMessage = 'Log out error';

      if (isAxiosError(err)) {
        errorMessage =
          err.response?.data?.message ?? err.message ?? 'Unknow server error';
        console.error('Axios logout error:', err);
      } else {
        console.error('Unexpected logout error:', err);
      }

      set({
        error: errorMessage,
        isLoading: false,
      });
      set({ user: null });
    }
  },
}));
