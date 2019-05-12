using System.Collections.Generic;

namespace DelControlWeb.ViewModels.Couriers
{
    public class SelectOrder
    {
        public string CourierId { get; set; }

        public List<Models.Order> Orders { get; set; }
    }
}