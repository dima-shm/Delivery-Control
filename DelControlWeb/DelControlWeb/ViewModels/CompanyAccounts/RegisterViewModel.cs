using System;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.CompanyAccounts
{
    public class RegisterViewModel
    {
        [Required]
        public string CompanyName { get; set; }

        [Required]
        [RegularExpression(@"^[0-9 ]+$")]
        public string RegisterNumber { get; set; }

        [Required]
        [DataType(DataType.Date)]
        public DateTime RegisterDate { get; set; }

        [Required]
        [RegularExpression(@"^[0-9 ]+$")]
        public string TaxpayerNumber { get; set; }

        [Required]
        [MinLength(10)]
        public string CompanyAddress { get; set; }

        [Required]
        [MinLength(6)]
        public string UserName { get; set; }

        [Required]
        [EmailAddress]
        public string Email { get; set; }

        [Required]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        [Required]
        [Compare("Password")]
        [DataType(DataType.Password)]
        public string PasswordConfirm { get; set; }
    }
}