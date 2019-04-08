using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.Account;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using System.Security.Claims;
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

        private ApplicationRoleManager RoleManager
        {
            get
            {
                return HttpContext.GetOwinContext().GetUserManager<ApplicationRoleManager>();
            }
        }

        private IAuthenticationManager AuthenticationManager
        {
            get
            {
                return HttpContext.GetOwinContext().Authentication;
            }
        }

        public ActionResult Register()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Register(RegisterViewModel model)
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
                IdentityResult result = CreateAcount(user, model.Password, "company");
                if (result.Succeeded)
                {
                    return RedirectToAction("Login", "Account");
                }
                else
                {
                    AddErrors(result);
                }
            }
            return View(model);
        }

        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Login(LoginViewModel model)
        {
            if (ModelState.IsValid)
            {
                User user = await UserManager.FindAsync(model.Email, model.Password);
                if (user == null)
                {
                    AddErrors(new IdentityResult("Invalid login or password."));
                }
                else
                {
                    ClaimsIdentity claim = await UserManager.CreateIdentityAsync(user,
                                            DefaultAuthenticationTypes.ApplicationCookie);
                    AuthenticationManager.SignOut();
                    AuthenticationManager.SignIn(new AuthenticationProperties { IsPersistent = true }, claim);
                    return RedirectToAction("Index", "Home");
                }
            }
            return View(model);
        }

        public ActionResult Logout()
        {
            AuthenticationManager.SignOut();
            return RedirectToAction("Login");
        }

        private void AddErrors(IdentityResult result)
        {
            foreach (string error in result.Errors)
            {
                ModelState.AddModelError(string.Empty, error);
            }
        }

        private IdentityResult CreateAcount(User user, string userPassword, string roleName)
        {
            IdentityResult result = UserManager.Create(user, userPassword);
            if (result.Succeeded)
            {
                UserManager.AddToRole(user.Id, roleName);
            }
            return result;
        }
    }
}