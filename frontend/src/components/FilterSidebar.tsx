import SearchFilter from './searchFilter/SearchFilter.tsx';

export default function FilterSidebar() {
  return (
    <aside
      className="scrollbar-none fixed top-(--sidebar-y-indent)
        right-(--sidebar-x-indent) h-(--sidebar-height)
        w-(--filter-sidebar-width) overflow-y-auto border-l-2 border-gray-150
        bg-white p-5"
    >
      <SearchFilter />
    </aside>
  );
}
