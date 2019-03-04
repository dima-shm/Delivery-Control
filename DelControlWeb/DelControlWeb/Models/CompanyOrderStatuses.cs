using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class CompanyOrderStatuses
    {
        [Key]
        public int Id { get; set; }

        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public int? OrderStatusId { get; set; }
        [ForeignKey("OrderStatusId")]
        private OrderStatus OrderStatus { get; set; }
    }
}