using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.CompanyAccounts;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using Microsoft.Owin.Security;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace DelControlWeb.Controllers
{
    public class CompanyAccountsController : Controller
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

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
        [Authorize(Roles = "admin")]
        public ActionResult Index()
        {
            return View(db.Companies.ToList());
        }

        [HttpGet]
        [Authorize]
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Company company = db.Companies.Find(id);
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        [HttpGet]
        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public async Task<ActionResult> Create(CreateViewModel model)
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
                    UserName = model.UserName,
                    Email = model.Email
                };
                IdentityResult result = await CreateAcountAsync(user, model.Password, "company");
                if (result.Succeeded)
                {
                    return RedirectToAction("Login", "Accounts");
                }
                else
                {
                    AddErrors(result);
                }
            }
            return View(model);
        }

        [HttpGet]
        [Authorize(Roles = "admin")]
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Company company = db.Companies.Find(id);
            EditViewModel model = new EditViewModel()
            {
                Id = company.Id,
                CompanyName = company.Name,
                RegisterNumber = company.RegisterNumber,
                RegisterDate = company.RegisterDate,
                TaxpayerNumber = company.TaxpayerNumber,
                CompanyAddress = company.Address
            };
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "admin")]
        public ActionResult Edit(EditViewModel model)
        {
            if (ModelState.IsValid)
            {
                Company company = new Company()
                {
                    Id = model.Id,
                    Name = model.CompanyName,
                    RegisterNumber = model.RegisterNumber,
                    RegisterDate = model.RegisterDate,
                    TaxpayerNumber = model.TaxpayerNumber,
                    Address = model.CompanyAddress
                };
                db.Entry(company).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(model);
        }

        [HttpGet]
        [Authorize(Roles = "admin")]
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Company company = db.Companies.Find(id);
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        [HttpPost, ActionName("Delete")]
        [Authorize(Roles = "admin")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Company company = db.Companies.Find(id);
            db.Companies.Remove(company);
            db.SaveChanges();
            return RedirectToAction("Index");
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