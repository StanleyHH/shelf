import { Link } from 'react-router';

interface Props {
  actorId: number;
  name: string;
  role: string;
  image: string;
}

export default function ActorCard({
  actorId,
  name,
  role,
  image,
}: Readonly<Props>) {
  return (
    <Link
      to={'/people/' + actorId}
      className="group flex cursor-pointer items-center gap-3"
    >
      <img
        className="h-16 w-16 rounded-full object-cover object-top"
        src={image}
        alt={name}
      />
      <div className="leading-4">
        <div className="font-bold group-hover:underline">{name}</div>
        <div className="text-sm text-neutral-400">{role}</div>
      </div>
    </Link>
  );
}
