import { Link } from 'react-router';

import useShowQueryStore from '../store.ts';

interface Props {
  item: string;
  onClick: (item: string) => void;
}

export default function FilterLink({ item, onClick }: Readonly<Props>) {
  const reset = useShowQueryStore((s) => s.reset);

  return (
    <Link
      to="/shows"
      className="text-sky-600 hover:underline"
      onClick={() => {
        reset();
        onClick(item);
      }}
    >
      {item}
    </Link>
  );
}
