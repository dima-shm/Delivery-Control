using Microsoft.AspNet.Identity.EntityFramework;

namespace DelControlWeb.Models
{
    public class Role : IdentityRole
    {
        public Role() { }

        public string Description { get; set; }
    }
}