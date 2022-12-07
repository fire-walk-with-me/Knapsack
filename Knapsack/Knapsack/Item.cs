using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Knapsack
{
    public class Item : IComparable<Item>
    {
        public float weight;
        public float value;
        public string itemName;

        public float weightValueRatio = 0;

        public Item(float weight, float value, string itemName)
        {
            this.weight = weight;
            this.value = value;
            this.itemName = itemName;

            if(value != 0 && weight != 0) weightValueRatio = value / weight;
            else weightValueRatio = value + weight;
        }

        public int CompareTo(Item compare)
        {
            if (compare == null) return 1;

            else return this.weightValueRatio.CompareTo(compare.weightValueRatio);
        }
    }
}
