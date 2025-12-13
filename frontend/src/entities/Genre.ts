import type Item from './Item.ts';

export default interface Genre extends Item{
  id: string;
  name: string;
}
