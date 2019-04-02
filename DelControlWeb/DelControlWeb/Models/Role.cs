using Microsoft.AspNet.Identity.EntityFramework;

namespace DelControlWeb.Models
{
    public class Role : IdentityRole
    {
        public string Description { get; set; }
    }
}