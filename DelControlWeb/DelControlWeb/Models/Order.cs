using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class Order
    {
        [Key]
        public int Id { get; set; }

        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public int? CustomerId { get; set; }
        [ForeignKey("CustomerId")]
        private Customer Customer { get; set; }

        public int? DeliveryId { get; set; }
        [ForeignKey("DeliveryId")]
        private Delivery Delivery { get; set; }

        public int? CourierId { get; set; }
        [ForeignKey("CourierId")]
        private User Courier { get; set; }

        public string Status { get; set; }
    }
}