import ShowStatusLabel from './ShowStatusLabel.tsx';

interface Props {
  status: string;
}

export default function ShowCard({ status }: Readonly<Props>) {
  return (
    <div className="flex flex-col gap-1">
      <img
        src="https://static.tvmaze.com/uploads/images/original_untouched/220/550800.jpg"
        alt="Show Title"
      />
      <div className="flex items-start gap-2">
        <p className="text-base font-bold text-sky-600">Show Title</p>
        <ShowStatusLabel status={status} />
      </div>
      <p className="text-sm/4 text-[#999999]">
        2024 &#8226; US &#8226; Crime, Drama, Thriller
      </p>
    </div>
  );
}
