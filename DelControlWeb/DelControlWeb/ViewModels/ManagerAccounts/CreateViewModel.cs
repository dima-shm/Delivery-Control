using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.ManagerAccounts
{
    public class CreateViewModel
    {
        [Required]
        [MinLength(6)]
        public string UserName { get; set; }

        [Required]
        public string Phone { get; set; }

        [Required]
        public string Address { get; set; }

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