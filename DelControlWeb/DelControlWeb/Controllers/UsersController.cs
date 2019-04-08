using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.Users;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class UsersController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        private ApplicationUserManager UserManager
        {
            get
            {
                return HttpContext.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
        }

        public ActionResult Index()
        {
            User currentUser = db.Users.Find(User.Identity.GetUserId());
            return View(db.Users.Where(u => u.CompanyId == currentUser.CompanyId).ToList());
        }

        public ActionResult Details(string id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Name,Phone,Address,Email,Password,PasswordConfirm")] CreateViewModel model)
        {
            if (ModelState.IsValid)
            {
                User currentUser = UserManager.FindById(User.Identity.GetUserId());
                User user = new User
                {
                    CompanyId = currentUser.CompanyId,
                    Name = model.Name,
                    UserName = model.Name,
                    Phone = model.Phone,
                    Address = model.Address,
                    Email = model.Email,
                };
                IdentityResult result = CreateAcount(user, model.Password, "manager");
                if (result.Succeeded)
                {
                    return RedirectToAction("Index");
                }
                else
                {
                    AddErrors(result);
                }
            }
            return View(model);
        }

        public ActionResult Edit(string id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            EditViewModel model = new EditViewModel
            {
                Id = user.Id,
                Name = user.Name,
                Phone = user.Phone,
                Address = user.Address,
                Email = user.Email
            };
            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Id,Name,Phone,Address,Email")] EditViewModel model)
        {
            if (ModelState.IsValid)
            {
                User user = UserManager.FindById(model.Id);
                if (user != null)
                {
                    user.Name = model.Name;
                    user.UserName = model.Email;
                    user.Phone = model.Phone;
                    user.Address = model.Address;
                    user.Email = model.Email;
                    IdentityResult result = UserManager.Update(user);
                    if (result.Succeeded)
                    {
                        return RedirectToAction("Index");
                    }
                    else
                    {
                        AddErrors(result);
                    }
                }
            }
            return View(model);
        }

        public ActionResult Delete(string id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            User user = db.Users.Find(id);
            if (user == null)
            {
                return HttpNotFound();
            }
            return View(user);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(string id)
        {
            User user = db.Users.Find(id);
            db.Users.Remove(user);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
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