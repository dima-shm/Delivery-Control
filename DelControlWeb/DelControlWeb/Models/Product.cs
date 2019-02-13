using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.Models
{
    public class Product
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }

        public string Descriotion { get; set; }

        public string Price { get; set; }
    }
}