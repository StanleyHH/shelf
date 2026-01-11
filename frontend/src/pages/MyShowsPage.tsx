import { useState } from 'react';

import yourAd from '../assets/your_ad.jpg';
import Breadcrumb from '../components/Breadcrumb.tsx';
import MyShowSecondaryTitle from '../components/MyShowSecondaryTitle.tsx';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';

export default function MyShows() {
  const [isWatchingActive, setIsWatchingActive] = useState(true);
  return (
    <>
      <Breadcrumb
        navLinks={[{ label: 'Home', to: '/' }, { label: 'My Shows' }]}
      />
      <p className="mt-2 text-2xl">My Shows</p>

      <div className="mt-5 flex gap-7">
        <MyShowSecondaryTitle
          title="Watching"
          quantity="4"
          isActive={isWatchingActive}
          onClick={() => setIsWatchingActive(true)}
        />
        <MyShowSecondaryTitle
          title="Plan to Watch"
          quantity="12"
          isActive={!isWatchingActive}
          onClick={() => setIsWatchingActive(false)}
        />
      </div>

      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
