import { Link } from 'react-router';

export default function Breadcrumb() {
  return (
    <div className="text-sm">
      <Link to="/shows" className="text-sky-600 hover:underline">
        Home
      </Link>
      <span className="mx-2 text-neutral-400">&gt;</span>
      <Link to="/shows" className="text-sky-600 hover:underline">
        Shows
      </Link>
    </div>
  );
}
