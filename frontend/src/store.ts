import { create } from 'zustand';

interface ShowQuery {
  searchText?: string;
  genreName?: string;
  countryName?: string;
  status?: string;
  year?: string;
}

interface ShowQueryStore {
  showQuery: ShowQuery;
  setSearchText: (searchText: string) => void;
  setGenreName: (genreId: string) => void;
  setCountryName: (countryId: string) => void;
  setStatus: (status: string) => void;
  setYear: (year: string) => void;
}

const useShowQueryStore = create<ShowQueryStore>((set) => ({
  showQuery: {},
  setSearchText: (searchText) =>
    set((store) => ({ showQuery: { ...store.showQuery, searchText } })),
  setGenreName: (genreName) =>
    set((store) => ({ showQuery: { ...store.showQuery, genreName } })),
  setCountryName: (countryName) =>
    set((store) => ({ showQuery: { ...store.showQuery, countryName } })),
  setStatus: (status) =>
    set((store) => ({ showQuery: { ...store.showQuery, status } })),
  setYear: (year) =>
    set((store) => ({ showQuery: { ...store.showQuery, year } })),
}));

export default useShowQueryStore;
