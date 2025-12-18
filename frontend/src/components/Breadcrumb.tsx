import { Link } from 'react-router';

interface BreadCrumbNavLink {
  label: string;
  to?: string;
}

interface Props {
  navLinks: BreadCrumbNavLink[];
}

export default function Breadcrumb({ navLinks }: Readonly<Props>) {
  return (
    <nav className="flex text-sm">
      {navLinks.map((item, index) => (
        <div key={item.label} className="flex items-center">
          {index > 0 && <span className="mx-2 text-neutral-400">&gt;</span>}
          {item.to ? (
            <Link to={item.to} className="text-sky-600 hover:underline">
              {item.label}
            </Link>
          ) : (
            <span className="text-neutral-400">{item.label}</span>
          )}
        </div>
      ))}
    </nav>
  );
}
