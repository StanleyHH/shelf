import { BsPersonCircle } from 'react-icons/bs';
import { IoCloseSharp } from 'react-icons/io5';
import Modal from 'react-modal';

import SocialButtonsRow from './SocialButtonsRow.tsx';

interface Props {
  isOpen: boolean;
  onClick: (q: boolean) => void;
}

export default function LoginModal({ isOpen, onClick }: Readonly<Props>) {
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={() => onClick(false)}
      className="mx-auto w-95 rounded-xl bg-white p-5"
      overlayClassName="fixed inset-0 bg-black/60 flex items-center justify-center"
    >
      <div className="flex justify-between border-b border-gray-300 pb-5">
        <div className="flex items-center gap-2">
          <BsPersonCircle size={25} />
          <div className="text-xl font-bold">Sign In</div>
        </div>
        <button
          className="cursor-pointer text-gray-300 hover:text-black"
          onClick={() => onClick(false)}
        >
          <IoCloseSharp size={30} />
        </button>
      </div>
      <form className="mt-5 space-y-4 px-7">
        <label className="block text-gray-400">
          Username<input
            className="mt-1 w-full rounded-sm border border-gray-300 px-2 py-2
              pr-7 text-black focus:border-gray-700 focus:outline-none"
          />
        </label>

        <label className="block text-gray-400">
          Password<input
            className="mt-1 w-full rounded-sm border border-gray-300 px-2 py-2
              pr-7 text-black focus:border-gray-700 focus:outline-none"
          />
        </label>
        <button
          onClick={(e) => e.preventDefault()}
          className="w-full rounded-sm bg-sky-500 p-2 font-bold text-white
            duration-200 hover:cursor-pointer hover:bg-sky-400"
        >
          Log In
        </button>
      </form>
      <div className="mt-3 flex justify-between px-17 text-sm">
        <div className="cursor-pointer text-blue-700 hover:underline">
          Sign Up
        </div>
        <div className="cursor-pointer hover:underline">
          Forgot your password?
        </div>
      </div>
      <div className="relative my-6">
        <div className="absolute inset-0 flex items-center">
          <div className="w-full border-t border-gray-300"></div>
        </div>
        <div className="relative flex justify-center text-sm">
          <span className="bg-white px-4 text-gray-500">or continue with</span>
        </div>
      </div>
      <SocialButtonsRow />
    </Modal>
  );
}
