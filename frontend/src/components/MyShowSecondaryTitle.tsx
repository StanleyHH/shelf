import Counter from './Counter.tsx';

interface Props {
  title: string;
  quantity: string | number;
  isActive?: boolean;
  onClick: () => void;
}

export default function MyShowSecondaryTitle({
  title,
  quantity,
  isActive = false,
  onClick,
}: Readonly<Props>) {
  return (
    <button
      onClick={onClick}
      className={`relative cursor-pointer text-xl font-bold ${
        isActive
          ? 'border-b-2 border-b-red-500 text-black'
          : 'text-neutral-400 hover:text-neutral-500'
        }`}
    >
      {title}
      <Counter value={quantity} />
    </button>
  );
}
