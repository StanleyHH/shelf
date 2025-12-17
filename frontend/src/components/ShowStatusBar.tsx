interface Props {
  label: string;
  isActive?: boolean;
}

export default function ShowStatusBar({
  label,
  isActive = false,
}: Readonly<Props>) {
  return (
    <div
      className={`w-full cursor-pointer py-2.5 text-center transition ${
        isActive
          ? 'bg-red-700 font-bold text-white'
          : 'bg-neutral-100 hover:bg-neutral-200'
        } `}
    >
      {label}
    </div>
  );
}
