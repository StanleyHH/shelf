import type Item from './Item.ts';

export default interface Status extends Item {
  id: string;
  name: string;
  type: 'status';
}

export const STATUSES = [
  { id: 'ended', name: 'Cancelled', type: 'status' },
  { id: 'ongoing', name: 'Ongoing', type: 'status' },
  { id: 'on_break', name: 'On break', type: 'status' },
];
