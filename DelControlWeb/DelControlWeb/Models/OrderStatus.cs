using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.Models
{
    public class OrderStatus
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }
    }
}