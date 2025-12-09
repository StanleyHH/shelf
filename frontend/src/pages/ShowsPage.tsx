import InfiniteScroll from 'react-infinite-scroll-component';

import ShowCard from '../components/ShowCard.tsx';
import useShows from '../hooks/useShows.ts';

export default function ShowsPage() {
  const { data, fetchNextPage, hasNextPage } = useShows();

  const fetchedShowsCount =
    data?.pages.reduce((total, page) => total + page.content.length, 0) || 0;

  return (
    <>
      <p className="text-3xl">TV Shows</p>
      <InfiniteScroll
        next={fetchNextPage}
        hasMore={hasNextPage}
        loader={<p>Loading...</p>}
        dataLength={fetchedShowsCount}
        className="scrollbar-none"
      >
        <div className="mt-5 grid grid-cols-2 gap-5 md:grid-cols-4">
          {data?.pages.map((page) => {
            return page.content.map((show) => (
              <ShowCard key={show.id} show={show} />
            ));
          })}
        </div>
      </InfiniteScroll>
    </>
  );
}
