interface Props {
  onClick: () => void;
  label: string;
}

export default function SearchFilterToggleButton({
  onClick,
  label,
}: Readonly<Props>) {
  return (
    <button
      className="cursor-pointer text-red-500 hover:underline"
      onClick={onClick}
    >
      {label}
    </button>
  );
}
