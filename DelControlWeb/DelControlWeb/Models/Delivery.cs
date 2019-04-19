using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class Delivery
    {
        [Key]
        public int Id { get; set; }

        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public string Address { get; set; }

        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime Date { get; set; }

        [DataType(DataType.Time)]
        public DateTime Time { get; set; }
    }
}