import { isRouteErrorResponse, useRouteError } from 'react-router';

import yourAd from '../assets/your_ad.jpg';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';

export default function ErrorPage() {
  const error = useRouteError();

  return (
    <>
      <div className="text-red-600">
        {isRouteErrorResponse(error)
          ? 'This page does not exists.'
          : 'An unexpected error occurred.'}
      </div>
      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
