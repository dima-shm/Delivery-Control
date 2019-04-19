using DelControlWeb.Models;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.ViewModels.Order
{
    public class EditViewModel
    {
        public int OrderId { get; set; }

        public int? CustomerId { get; set; }
        [ForeignKey("CustomerId")]
        private Customer Customer { get; set; }

        public int? DeliveryId { get; set; }
        [ForeignKey("DeliveryId")]
        private Delivery Delivery { get; set; }

        public List<Product> Products { get; set; }

        public List<int> ProductIds { get; set; }
    }
}