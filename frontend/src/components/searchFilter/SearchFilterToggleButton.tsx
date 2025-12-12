interface Props {
  toggleViewAllItems: () => void;
  getToggleButtonTitle: () => string;
}

export default function SearchFilterToggleButton({
  toggleViewAllItems,
  getToggleButtonTitle,
}: Readonly<Props>) {
  return (
    <button
      className="cursor-pointer text-red-500 hover:underline"
      onClick={toggleViewAllItems}
    >
      {getToggleButtonTitle()}
    </button>
  );
}
