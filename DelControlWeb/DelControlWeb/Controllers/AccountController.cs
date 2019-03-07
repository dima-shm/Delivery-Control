using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace DelControlWeb.Controllers
{
    public class AccountController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        private ApplicationUserManager UserManager
        {
            get
            {
                return HttpContext.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
        }

        public ActionResult Register()
        {
            return View();
        }

        [HttpPost]
        public async Task<ActionResult> Register(RegisterViewModel model)
        {
            if (ModelState.IsValid)
            {
                Company company = new Company
                {
                    Name = model.CompanyName,
                    RegisterNumber = model.RegisterNumber,
                    RegisterDate = model.RegisterDate,
                    TaxpayerNumber = model.TaxpayerNumber,
                    Address = model.CompanyAddress
                };
                db.Companies.Add(company);
                db.SaveChanges();
                User user = new User
                {
                    CompanyId = company.Id,
                    Name = model.UserName,
                    UserName = model.Email,
                    Email = model.Email
                };
                IdentityResult result = await UserManager.CreateAsync(user, model.Password);
                if (result.Succeeded)
                {
                    
                    return RedirectToAction("Login", "Account");
                }
                else
                {
                    foreach (string error in result.Errors)
                    {
                        ModelState.AddModelError("", error);
                    }
                }
            }
            return View(model);
        }
    }
}