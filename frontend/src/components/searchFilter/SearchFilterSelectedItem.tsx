import { IoCloseSharp } from 'react-icons/io5';

interface Props {
  selected?: string;
  onClick: () => void;
}

export default function SearchFilterSelectedItem({
  selected,
  onClick,
}: Readonly<Props>) {
  return (
    <button
      onClick={onClick}
      className="flex cursor-pointer items-center gap-1 rounded-sm bg-black py-1
        pr-2 pl-3 whitespace-nowrap text-white"
    >
      {selected}
      <IoCloseSharp className="size-5" />
    </button>
  );
}
