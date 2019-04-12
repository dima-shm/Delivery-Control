using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.ManagerAccounts
{
    public class EditViewModel
    {
        public string Id { get; set; }

        [Required]
        public string UserName { get; set; }

        [Required]
        public string Phone { get; set; }

        [Required]
        public string Address { get; set; }

        [Required]
        public string Email { get; set; }
    }
}