export default function Header() {
  return (
    <header
      className="fixed top-0 left-0 z-50 flex h-(--headbar-height) w-full
        items-center justify-center border-b-3 border-b-red-700 bg-black-90
        text-white"
    >
      <h1 className="text-xl font-bold">Hello Header</h1>
    </header>
  );
}
