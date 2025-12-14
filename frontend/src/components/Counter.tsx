interface Props {
  value: string;
}

export default function Counter({ value }: Readonly<Props>) {
  return (
    <span
      className="absolute -top-1 pl-1 text-[12px] font-normal text-neutral-500"
    >
      {value}
    </span>
  );
}
