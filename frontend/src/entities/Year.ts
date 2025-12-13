import type Item from './Item.ts';

export default interface Year extends Item {
  id: string;
  name: string;
  type: 'year';
}

export const YEARS: Year[] = Array.from({ length: 2026 - 1890 + 1 }, (_, i) => {
  const year = 2026 - i;
  return {
    id: year.toString(),
    name: year.toString(),
    type: 'year',
  };
});
