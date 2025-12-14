import type { IconType } from 'react-icons';

interface Props {
  Icon: IconType;
  label: string;
}

export default function MainSidebarItem({ Icon, label }: Readonly<Props>) {
  return (
    <div
      className="group flex flex-col items-center border-b border-neutral-400
        pt-5 pb-2 text-[#d4d4d4] transition hover:cursor-pointer hover:bg-white
        hover:text-red-700"
    >
      <Icon size={40} />
      <div className="text-sm text-neutral-400 group-hover:text-black">
        {label}
      </div>
    </div>
  );
}
