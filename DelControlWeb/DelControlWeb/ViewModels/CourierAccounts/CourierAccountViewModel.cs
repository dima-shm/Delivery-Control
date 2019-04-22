using DelControlWeb.Models;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.ViewModels.CourierAccounts
{
    public class CourierAccountViewModel
    {
        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public string Name { get; set; }

        public string Email { get; set; }

        public string Phone { get; set; }

        public string Address { get; set; }

        public string Password { get; set; }
    }
}