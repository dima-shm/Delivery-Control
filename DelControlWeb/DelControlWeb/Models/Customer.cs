using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class Customer
    {
        [Key]
        public int Id { get; set; }

        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public string Name { get; set; }

        public string Phone { get; set; }

        public string Address { get; set; }
    }
}