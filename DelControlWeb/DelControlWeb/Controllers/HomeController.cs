using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using System.Web;
using System.Web.Mvc;

namespace DelControlWeb.Controllers
{
    public class HomeController : Controller
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

        private ApplicationUserManager UserManager
        {
            get
            {
                return HttpContext.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
        }

        [HttpGet]
        public ActionResult Index()
        {
            User currentUser = db.Users.Find(User.Identity.GetUserId());
            return View(currentUser);
        }

        [HttpGet]
        public ActionResult About()
        {
            return View();
        }
    }
}