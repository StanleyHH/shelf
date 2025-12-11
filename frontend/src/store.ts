import { create } from 'zustand';

interface ShowQuery {
  searchText?: string;
  genreName?: string;
  countryName?: string;
  status?: string;
  year?: number;
}

interface ShowQueryStore {
  showQuery: ShowQuery;
  setSearchText: (searchText: string) => void;
  setGenreId: (genreId: string) => void;
  setCountryId: (countryId: string) => void;
  setStatus: (status: string) => void;
  setYear: (year: number) => void;
}

const useShowQueryStore = create<ShowQueryStore>((set) => ({
  showQuery: {},
  setSearchText: (searchText) => set(() => ({ showQuery: { searchText } })),
  setGenreId: (genreName) =>
    set((store) => ({ showQuery: { ...store.showQuery, genreName } })),
  setCountryId: (countryName) =>
    set((store) => ({ showQuery: { ...store.showQuery, countryName } })),
  setStatus: (status) =>
    set((store) => ({ showQuery: { ...store.showQuery, status } })),
  setYear: (year) =>
    set((store) => ({ showQuery: { ...store.showQuery, year } })),
}));

export default useShowQueryStore;
