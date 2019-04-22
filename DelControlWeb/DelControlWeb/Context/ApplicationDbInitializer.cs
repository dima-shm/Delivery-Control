using DelControlWeb.Managers;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Data.Entity;

namespace DelControlWeb.Context
{
    public class ApplicationDbInitializer : DropCreateDatabaseIfModelChanges<ApplicationContext>
    {
        private ApplicationUserManager _userManager;

        private RoleManager<IdentityRole> _roleManager;

        protected override void Seed(ApplicationContext context)
        {
            _userManager = new ApplicationUserManager(new UserStore<User>(context));
            _roleManager = new RoleManager<IdentityRole>(new RoleStore<IdentityRole>(context));
            CreateRoles(new[] { "admin", "company", "manager", "courier" });
            User admin = new User { Email = "admin@admin.com", UserName = "admin" };
            string password = "admin123";
            CreateAdmin(admin, password, "admin");
            base.Seed(context);
        }

        private void CreateRoles(string[] rolesNames)
        {
            foreach (string name in rolesNames)
            {
                var role = new IdentityRole { Name = name };
                _roleManager.Create(role);
            }
        }

        private void CreateAdmin(User admin, string password, string roleName)
        {
            var result = _userManager.Create(admin, password);
            if (result.Succeeded)
            {
                _userManager.AddToRole(admin.Id, roleName);
            }
        }
    }
}