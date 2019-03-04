using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.Models
{
    public class CourierStatus
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }
    }
}