import { type ReactNode } from 'react';

interface Props {
  title: string;
  children: ReactNode;
}

export default function InfoRow({ title, children }: Readonly<Props>) {
  return (
    <div className="flex py-1">
      <div className="flex-1 text-neutral-400">{title}</div>
      <div className="flex-1">{children}</div>
    </div>
  );
}
