using DelControlWeb.Models;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Data.Entity;

namespace DelControlWeb.Context
{
    public class ApplicationContext : IdentityDbContext<User>
    {
        public DbSet<Company> Companies { get; set; }

        public DbSet<LastCourierLocation> LastCourierLocations { get; set; }

        public DbSet<Customer> Customers { get; set; }

        public DbSet<Delivery> Delivery { get; set; }

        public DbSet<Product> Products { get; set; }

        public DbSet<Order> Orders { get; set; }

        public DbSet<OrderProducts> OrderProducts { get; set; }

        public ApplicationContext() : base("DelControlDB") { }

        public static ApplicationContext Create()
        {
            return new ApplicationContext();
        }
    }
}