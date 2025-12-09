import type Show from '../entities/Show.ts';
import ShowStatusLabel from './ShowStatusLabel.tsx';

interface Props {
  show: Show;
}

export default function ShowCard({ show }: Readonly<Props>) {
  return (
    <div className="flex flex-col gap-1">
      <img src={show.imageUrl} alt={show.title} />
      <div className="flex items-start gap-2">
        <p className="text-base/5 font-bold text-sky-600">{show.title}</p>
        <ShowStatusLabel status={show.status} />
      </div>
      <p className="text-sm/4 text-[#999999]">
        {show.startYear} &#8226; US &#8226; Crime, Drama, Thriller
      </p>
    </div>
  );
}
