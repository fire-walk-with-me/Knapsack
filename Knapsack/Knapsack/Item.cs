using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Knapsack
{
    public class Item
    {
        public int weight;
        public int value;
        public string itemName;

        public float weightValueRatio = 0;

        public Item(int weight, int value, string itemName)
        {
            this.weight = weight;
            this.value = value;
            this.itemName = itemName;

            if(value != 0 && weight != 0) weightValueRatio = value / weight;
            else weightValueRatio = value + weight;
        }
    }
}
