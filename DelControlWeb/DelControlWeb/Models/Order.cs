using System;
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

        public string CustomerName { get; set; }

        public string DeliveryAddress { get; set; }

        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime DeliveryDate { get; set; }

        [DataType(DataType.Time)]
        public DateTime DeliveryTime { get; set; }

        public string Comment { get; set; }

        public string CourierId { get; set; }
        [ForeignKey("CourierId")]
        private User Courier { get; set; }

        public string Status { get; set; }
    }
}