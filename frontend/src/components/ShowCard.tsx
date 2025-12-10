import type Show from '../entities/Show.ts';
import useCountries from '../hooks/useCountries.ts';
import useGenres from '../hooks/useGenres.ts';
import ShowStatusLabel from './ShowStatusLabel.tsx';

interface Props {
  show: Show;
}

export default function ShowCard({ show }: Readonly<Props>) {
  const { data: allGenres = [] } = useGenres();
  const { data: allCountries = [] } = useCountries();

  const genreNames = show.genres
    .map((genreId) => allGenres.find((g) => g.id === genreId)?.name)
    .join(', ');

  const countryNames = show.countries
    .map((countryId) => allCountries.find((c) => c.id === countryId)?.name)
    .join(', ');

  return (
    <div className="flex flex-col gap-1">
      <img
        className="h-25 object-contain"
        src={show.imageUrl}
        alt={show.title}
      />
      <div className="flex items-start justify-between">
        <p className="text-base/5 font-bold text-sky-600">{show.title}</p>
        <ShowStatusLabel status={show.status} />
      </div>
      <p className="text-sm/4 text-[#999999]">
        {show.startYear} &#8226; {countryNames} &#8226; {genreNames}
      </p>
    </div>
  );
}
