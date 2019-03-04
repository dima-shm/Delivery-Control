using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class CompanyCourierStatuses
    {
        [Key]
        public int Id { get; set; }

        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public int? CourierStatusId { get; set; }
        [ForeignKey("CourierStatusId")]
        private CourierStatus CourierStatus { get; set; }
    }
}