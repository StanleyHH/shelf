import { isRouteErrorResponse, useRouteError } from 'react-router';

export default function ErrorPage() {
  const error = useRouteError();

  return (
    <div className="text-red-600">
      {isRouteErrorResponse(error)
        ? 'This page does not exists.'
        : 'An unexpected error occurred.'}
    </div>
  );
}
