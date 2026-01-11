import yourAd from '../assets/your_ad.jpg';
import SecondSidebarContainer from '../components/SecondSidebarContainer.tsx';

export default function MyShows() {
  return (
    <>
      myshows
      <SecondSidebarContainer>
        <img src={yourAd} alt="your_ad" />
      </SecondSidebarContainer>
    </>
  );
}
