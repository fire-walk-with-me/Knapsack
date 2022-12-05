using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Knapsack
{
    public class Knapsack
    {
        public int ID;
        public float capacity;
        public float currentFill;
        public float currentValue;
        private List<Item> content = new List<Item>();

        public Knapsack(int ID, float capacity)
        {
            this.ID = ID;
            this.capacity = capacity;
            currentFill = 0;
            currentValue = 0;
        }

        public void AddContent(Item item)
        {
            content.Add(item);
            currentFill += item.weight;
            currentValue += item.value;
        }

        public void RemoveItem(Item item)
        {
            if (!content.Contains(item))
                return;

            content.Remove(item);
            currentFill -= item.weight;
            currentValue -= item.value;
        }

        public void RemoveItem(int i)
        {
            if (content.Count < i)
                return;

            currentFill -= content[i].weight;
            currentValue -= content[i].value;
            content.RemoveAt(i);
        }

        public void EmptyKanpsack()
        {
            content.Clear();
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
    }
}
