import InfiniteScroll from 'react-infinite-scroll-component';

import useShows from '../hooks/useShows.ts';
import Loader from './Loader.tsx';
import ShowCard from './ShowCard.tsx';

export default function ShowGrid() {
  const { data, fetchNextPage, hasNextPage, error, isLoading } = useShows();

  const fetchedShowsCount =
    data?.pages.reduce((total, page) => total + page.content?.length, 0) || 0;

  if (error) {
    return <p className="mt-5">{error.message}</p>;
  }

  if (!isLoading && fetchedShowsCount === 0) {
    return <p className="mt-5">No shows found.</p>;
  }

  return (
    <InfiniteScroll
      next={fetchNextPage}
      hasMore={hasNextPage}
      loader={<Loader />}
      dataLength={fetchedShowsCount}
      className="scrollbar-none"
      aria-label="TV shows list"
    >
      <div className="mt-5 grid grid-cols-2 gap-5 md:grid-cols-4">
        {data?.pages.flatMap((page) =>
          page.content.map((show) => <ShowCard key={show.id} show={show} />),
        )}
      </div>
    </InfiniteScroll>
  );
}
