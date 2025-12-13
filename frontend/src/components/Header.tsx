import { useEffect, useRef, useState } from 'react';
import { BsPersonFill } from 'react-icons/bs';
import { IoSearchOutline } from 'react-icons/io5';
import { RiBookShelfLine } from 'react-icons/ri';
import { Link } from 'react-router';

export default function Header() {
  const [isSearch, setIsSearch] = useState(false);
  const [searchValue, setSearchValue] = useState('');
  const searchRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (
        searchRef.current &&
        !searchRef.current.contains(event.target as Node)
      ) {
        setIsSearch(false);
        setSearchValue('');
      }
    }

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <header
      className="fixed top-0 left-0 z-50 flex h-(--headbar-height) w-full
        items-center justify-center border-b-3 border-b-red-700 bg-black-90
        text-white"
    >
      <div className="flex w-(--full-layout-width) items-center justify-center">
        <div className="flex grow items-center">
          <Link to="/" className="flex cursor-pointer gap-1 pl-4">
            <RiBookShelfLine size={35} />
            <div className="text-2xl">ShelF</div>
          </Link>

          <div className={`flex grow text-center ${isSearch ? 'hidden' : ''}`}>
            <div className="grow text-center text-neutral-400">
              <span className="cursor-pointer text-white">Shows</span>{' '}
              &nbsp;&nbsp; | &nbsp;&nbsp; Movies &nbsp;&nbsp; | &nbsp;&nbsp;
              Games &nbsp;&nbsp; | &nbsp;&nbsp; Books &nbsp;&nbsp; |
              &nbsp;&nbsp; Collections
            </div>
            <button
              className="cursor-pointer pr-10"
              onClick={() => setIsSearch(!isSearch)}
            >
              <IoSearchOutline size={25} className="mr-2" />
            </button>
          </div>
          <div
            ref={searchRef}
            className={`relative flex grow pl-10 text-black
              ${isSearch ? '' : 'hidden'}`}
          >
            <input
              value={searchValue}
              onChange={(e) => setSearchValue(e.target.value)}
              placeholder="Search shows, movies, games or books"
              className={`grow rounded-sm border bg-white p-1 pl-3
                focus:outline-none ${isSearch ? '' : 'hidden'}`}
            />
            <button
              className="cursor-pointer pr-10"
              onClick={() => setIsSearch(!isSearch)}
            >
              <IoSearchOutline size={25} className="absolute top-1 right-12" />
            </button>
          </div>
        </div>

        <div
          className="flex w-(--filter-sidebar-width) cursor-pointer items-center
            gap-3 pl-5"
        >
          <BsPersonFill size={25} />
          Log in
        </div>
      </div>
    </header>
  );
}
