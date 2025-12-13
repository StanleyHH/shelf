import { Gi3dGlasses, GiBookCover } from 'react-icons/gi';
import { IoGameController } from 'react-icons/io5';
import { PiMonitorPlayFill } from 'react-icons/pi';
import { RiMovie2Fill } from 'react-icons/ri';

import MainSidebarItem from './MainSidebarItem.tsx';

export default function MainSidebar() {
  return (
    <aside
      className="fixed top-(--sidebar-y-indent) left-(--sidebar-x-indent)
        h-(--sidebar-height) w-(--main-sidebar-width) bg-gray-150"
    >
      <MainSidebarItem Icon={PiMonitorPlayFill} label="My Shows" />
      <MainSidebarItem Icon={RiMovie2Fill} label="My Movies" />
      <MainSidebarItem Icon={IoGameController} label="My Games" />
      <MainSidebarItem Icon={GiBookCover} label="My Books" />
      <MainSidebarItem Icon={Gi3dGlasses} label="Profile" />
    </aside>
  );
}
