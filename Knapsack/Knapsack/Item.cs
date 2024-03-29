﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Knapsack
{
    public class Item
    {
        public bool avalible;
        public float weight;
        public float value;
        public string itemName;

        public float weightValueRatio = 0;

        public Item(float weight, float value, string itemName)
        {
            this.weight = weight;
            this.value = value;
            this.itemName = itemName;
            avalible = true;

            if(value != 0 && weight != 0) weightValueRatio = value / weight;
            else weightValueRatio = value + weight;
        }

        public void SetNewWeightValueRatio()
        {
            if (value != 0 && weight != 0) weightValueRatio = value / weight;
            else weightValueRatio = value + weight;
        }


    }
}
