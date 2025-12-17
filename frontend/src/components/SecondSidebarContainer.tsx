import type { JSX } from 'react';

interface Props {
  children?: JSX.Element;
}

export default function SecondSidebarContainer({ children }: Readonly<Props>) {
  return (
    <aside
      className="scrollbar-none fixed top-(--sidebar-y-indent)
        right-(--sidebar-x-indent) h-(--sidebar-height)
        w-(--filter-sidebar-width) overflow-y-auto border-l-2 border-gray-150
        bg-white px-5 pt-1"
    >
      {children}
    </aside>
  );
}
