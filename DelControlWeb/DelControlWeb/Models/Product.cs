using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class Product
    {
        [Key]
        public int Id { get; set; }

        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public string Name { get; set; }

        public string Descriotion { get; set; }

        public string Price { get; set; }
    }
}