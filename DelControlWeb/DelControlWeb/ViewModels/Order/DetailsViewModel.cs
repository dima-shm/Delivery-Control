using DelControlWeb.Models;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.ViewModels.Order
{
    public class DetailsViewModel
    {
        public int? CustomerId { get; set; }

        public string CustomerName { get; set; }

        public int? DeliveryId { get; set; }

        public string Delivery { get; set; }

        public string CourierId { get; set; }
        [ForeignKey("CourierId")]
        private User Courier { get; set; }

        public List<Product> Products { get; set; }

        public string Status { get; set; }
    }
}