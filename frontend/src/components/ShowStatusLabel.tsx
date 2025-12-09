interface Props {
  status: string;
}

interface StatusConfig {
  label: string;
  bgColor: string;
  textColor: string;
}

const STATUS_LABELS: Record<string, StatusConfig> = {
  ONGOING: {
    label: 'ON AIR',
    bgColor: 'bg-lime-600',
    textColor: 'text-white',
  },
  ON_BREAK: { label: 'PAUSE', bgColor: 'bg-white', textColor: 'text-gray-700' },
  ENDED: { label: 'ENDED', bgColor: 'bg-gray-400', textColor: 'text-white' },
};

export default function ShowStatusLabel({ status }: Props) {
  const config = STATUS_LABELS[status];

  return (
    <p
      className={`${config.bgColor} ${config.textColor} rounded-xs border px-0.5
        text-[8px]`}
    >
      {config.label}
    </p>
  );
}
