import type { IconType } from 'react-icons';
import { NavLink } from 'react-router';

import { useAuthStore } from '../authStore.ts';

interface Props {
  Icon: IconType;
  label: string;
  to: string;
}

export default function MainSidebarItem({ Icon, label, to }: Readonly<Props>) {
  const { user } = useAuthStore();
  return (
    <NavLink
      to={to}
      onClick={(e) => {
        if (!user) {
          e.preventDefault();
          e.stopPropagation();
        }
      }}
      className={({ isActive }) =>
        `group flex flex-col items-center border-b border-neutral-400 pt-5 pb-2
        text-[#d4d4d4] transition hover:cursor-pointer hover:bg-white
        hover:text-red-700 ${isActive ? 'bg-white text-red-700' : ''}`
      }
    >
      {({ isActive }) => (
        <>
          <Icon size={40} />
          <div
            className={`mt-1 text-sm ${
              isActive
                ? 'text-black'
                : 'text-neutral-400 group-hover:text-black'
            }`}
          >
            {label}
          </div>
        </>
      )}
    </NavLink>
  );
}
