using System;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.Account
{
    public class RegisterViewModel
    {
        [Required]
        public string CompanyName { get; set; }

        [Required]
        public string RegisterNumber { get; set; }

        [Required]
        public DateTime RegisterDate { get; set; }

        [Required]
        public string TaxpayerNumber { get; set; }

        [Required]
        public string CompanyAddress { get; set; }

        [Required]
        public string UserName { get; set; }

        [Required]
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