import { Link } from 'react-router';

interface Props {
  currentPage?: string;
}

export default function Breadcrumb({ currentPage }: Readonly<Props>) {
  return (
    <div className="flex text-sm">
      <Link to="/shows" className="text-sky-600 hover:underline">
        Home
      </Link>
      <span className="mx-2 text-neutral-400">&gt;</span>
      <Link to="/shows" className="text-sky-600 hover:underline">
        Shows
      </Link>
      {currentPage === 'showDetails' && (
        <div>
          <span className="mx-2 text-neutral-400">&gt;</span>
          <span className="text-neutral-400">Solar Opposites</span>
        </div>
      )}
    </div>
  );
}
