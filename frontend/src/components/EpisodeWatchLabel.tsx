import { FaRegCheckCircle, FaRegCircle } from 'react-icons/fa';
import { FaCircleCheck } from 'react-icons/fa6';

interface Props {
  isChecked: boolean;
}

export default function EpisodeWatchLabel({ isChecked }: Readonly<Props>) {
  return (
    <div className="group flex cursor-pointer items-center gap-5">
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
    </div>
  );
}
