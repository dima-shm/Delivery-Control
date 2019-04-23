using System;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.CompanyAccounts
{
    public class EditViewModel
    {
        public int Id { get; set; }

        [Required]
        public string CompanyName { get; set; }

        [Required]
        [RegularExpression(@"^[0-9 ]+$")]
        public string RegisterNumber { get; set; }

        [Required]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime RegisterDate { get; set; }

        [Required]
        [RegularExpression(@"^[0-9 ]+$")]
        public string TaxpayerNumber { get; set; }

        [Required]
        [MinLength(10)]
        public string CompanyAddress { get; set; }
    }
}