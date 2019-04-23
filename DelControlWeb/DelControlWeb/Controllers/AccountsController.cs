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
    public class AccountsController : Controller
    {
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

        [HttpGet]
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
                User user = await UserManager.FindByEmailAsync(model.Email);
                if (user != null)
                {
                    user = await UserManager.FindAsync(user.UserName, model.Password);
                    if (user != null)
                    {
                        ClaimsIdentity claim = await UserManager.CreateIdentityAsync(user,
                        DefaultAuthenticationTypes.ApplicationCookie);
                        AuthenticationManager.SignOut();
                        AuthenticationManager.SignIn(new AuthenticationProperties { IsPersistent = true }, claim);
                        return RedirectToAction("Index", "Home");
                    }
                }
                AddErrors(new IdentityResult("Invalid login or password."));
            }
            return View(model);
        }

        [HttpGet]
        public ActionResult Register()
        {
            return RedirectToAction("Create", "CompanyAccounts");
        }

        [HttpGet]
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

        private async Task<IdentityResult> CreateAcountAsync(User user, string userPassword, string roleName)
        {
            IdentityResult result = await UserManager.CreateAsync(user, userPassword);
            if (result.Succeeded)
            {
                UserManager.AddToRole(user.Id, roleName);
            }
            return result;
        }
    }
}