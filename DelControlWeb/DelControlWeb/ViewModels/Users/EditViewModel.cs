using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.Users
{
    public class EditViewModel
    {
        [Required]
        public string Name { get; set; }

        [Required]
        public string Phone { get; set; }

        [Required]
        public string Address { get; set; }

        [Required]
        public string Email { get; set; }
    }
}