import * as React from 'react';
import { FaRegCheckCircle, FaRegCircle } from 'react-icons/fa';
import { FaCircleCheck } from 'react-icons/fa6';

interface Props {
  isChecked: boolean;
  onClick: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

export default function EpisodeWatchLabel({
  isChecked,
  onClick,
}: Readonly<Props>) {
  return (
    <button
      onClick={onClick}
      className="group flex cursor-pointer items-center gap-5"
    >
      {isChecked ? (
        <FaCircleCheck className="size-6 text-lime-600" />
      ) : (
        <>
          <FaRegCircle className="size-6 text-neutral-200 group-hover:hidden" />
          <FaRegCheckCircle
            className="hidden size-6 text-neutral-200 group-hover:block"
          />
        </>
      )}
    </button>
  );
}
