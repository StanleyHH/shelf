import type Item from './Item.ts';

export default interface Country extends Item {
  id: string;
  name: string;
}
