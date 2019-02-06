using System.Data.Entity;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity.EntityFramework;

namespace DelControlWeb.Context
{
    public class ApplicationContext : IdentityDbContext<User>
    {
        public ApplicationContext() : base("DelControlDB") { }

        public static ApplicationContext Create()
        {
            return new ApplicationContext();
        }
    }
}