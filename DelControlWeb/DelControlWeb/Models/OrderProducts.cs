using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class OrderProducts
    {
        [Key]
        public int Id { get; set; }

        public int? OrderId { get; set; }
        [ForeignKey("OrderId")]
        private Order Order { get; set; }

        public string ProductName { get; set; }

        public string Descriotion { get; set; }

        public string Price { get; set; }
    }
}