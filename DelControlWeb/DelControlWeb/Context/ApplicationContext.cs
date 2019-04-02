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

        public DbSet<CourierStatus> CourierStatuses { get; set; }

        public DbSet<CompanyCourierStatuses> CompanyCourierStatuses { get; set; }

        public DbSet<OrderStatus> OrderStatuses { get; set; }

        public DbSet<CompanyOrderStatuses> CompanyOrderStatuses { get; set; }

        public ApplicationContext() : base("DelControlDB") { }

        public static ApplicationContext Create()
        {
            return new ApplicationContext();
        }

        public System.Data.Entity.DbSet<DelControlWeb.Models.Role> IdentityRoles { get; set; }
    }
}