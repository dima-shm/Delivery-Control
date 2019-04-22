using DelControlWeb.Models;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Data.Entity;

namespace DelControlWeb.Context
{
    public class ApplicationContext : IdentityDbContext<User>
    {
        public DbSet<Company> Companies { get; set; }

        public DbSet<CourierInfo> CourierInfoes { get; set; }

        public DbSet<Order> Orders { get; set; }

        public DbSet<OrderProducts> OrderProducts { get; set; }

        public DbSet<Role> IdentityRoles { get; set; }

        public ApplicationContext() : base("DelControlDB") { }

        public static ApplicationContext Create()
        {
            return new ApplicationContext();
        }
    }
}