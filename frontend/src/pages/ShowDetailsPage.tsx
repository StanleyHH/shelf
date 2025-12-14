import yourAd from '../assets/your_ad.jpg';
import Breadcrumb from '../components/Breadcrumb.tsx';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';

export default function ShowDetailsPage() {
  return (
    <>
      <Breadcrumb currentPage="showDetails" />
      <div>ShowDetailsPage</div>
      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
