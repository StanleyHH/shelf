export default interface Show {
  id: string;
  title: string;
  originalTitle: string;
  status: 'ONGOING' | 'ON_BREAK' | 'ENDED';
  startYear: number;
  imageUrl: string;
  countries: string[];
  genres: string[];
}
