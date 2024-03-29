﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Knapsack
{
    public class Knapsack
    {
        public float capacity;
        public float currentFill;
        public float currentValue;
        public List<Item> content = new List<Item>();

        public Knapsack(float capacity)
        {
            this.capacity = capacity;
            currentFill = 0;
            currentValue = 0;
        }

        public void AddContent(Item item)
        {
            if (item.weight + currentFill > capacity)
                return;

            item.avalible = false;
            content.Add(item);
            currentFill += item.weight;
            currentValue += item.value;

        }

        public void RemoveItem(Item item)
        {
            if (!content.Contains(item))
                return;

            item.avalible = true;
            content.Remove(item);
            currentFill -= item.weight;
            currentValue -= item.value;
        }

        public void RemoveItem(int i)
        {
            if (content.Count < i)
                return;

            content[i].avalible = true;
            currentFill -= content[i].weight;
            currentValue -= content[i].value;
            content.RemoveAt(i);
        }

        public void EmptyKanpsack()
        {
            foreach (Item item in content)
            {
                item.avalible = true;
            }
            content.Clear();
            currentFill = 0;
            currentValue = 0;
        }

        public Item findLeastValuableItem()
        {
            Item item = new Item(1, 100, "temp");

            foreach (Item i in content)
            {
                if (i.weightValueRatio < item.weightValueRatio) item = i;
            }

            return item;
        }

        public Item findMostValuableItem()
        {
            Item item = new Item(100, 1, "temp");

            foreach (Item i in content)
            {
                if (i.weightValueRatio > item.weightValueRatio) item = i;
            }

            return item;
        }

        public Item findLeastWeightItem()
        {
            Item item = new Item(100, 1, "temp");

            foreach (Item i in content)
            {
                if (i.weight < item.weight) item = i;
            }

            return item;
        }

        public float SpaceLeft()
        {
            return capacity - currentFill;
        }

        public bool HasSpaceLeft()
        {
            return currentFill < capacity;
        }

        public int contentCount()
        {
            return content.Count();
        }

        public Item Item(int index)
        {
            if (content.Count() < index)
                return null;

            return content[index];
        }
    }
}
