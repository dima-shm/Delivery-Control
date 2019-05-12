using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.Couriers;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class CouriersController : Controller
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
            string roleName = "courier";
            User currentUser = UserManager.FindById(User.Identity.GetUserId());
            var role = db.Roles.First(r => r.Name == roleName);
            var couriers = db.Users.Where(u => u.Roles.Any(r => r.RoleId == role.Id) && 
                u.CompanyId == currentUser.CompanyId);
            return View(couriers);
        }

        [HttpGet]
        public ActionResult SelectOrder(int? companyId, string courierId)
        {
            if (companyId == null || courierId == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            List<Order> orders = db.Orders
                .Where(o => o.CompanyId == companyId && o.CourierId == null).ToList();
            SelectOrder assignOrder = new SelectOrder
            {
                CourierId = courierId,
                Orders = orders
            };
            return View(assignOrder);
        }

        [HttpGet]
        public ActionResult AssignOrder(string courierId, int? orderId)
        {
            Order order = db.Orders.Find(orderId);
            order.CourierId = courierId;
            db.SaveChanges();
            return RedirectToAction("Index");
        }
        
    }
}
