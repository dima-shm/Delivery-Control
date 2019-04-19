using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class DeliveriesController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

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
            User currentUser = UserManager.FindById(User.Identity.GetUserId());
            List<Delivery> deliveries = db.Delivery.Where(d => d.CompanyId == currentUser.CompanyId).ToList();
            return View(deliveries);
        }

        [HttpGet]
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Delivery delivery = db.Delivery.Find(id);
            if (delivery == null)
            {
                return HttpNotFound();
            }
            return View(delivery);
        }

        [HttpGet]
        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(Delivery delivery)
        {
            if (ModelState.IsValid)
            {
                db.Delivery.Add(delivery);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(delivery);
        }

        [HttpGet]
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Delivery delivery = db.Delivery.Find(id);
            if (delivery == null)
            {
                return HttpNotFound();
            }
            return View(delivery);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Delivery delivery)
        {
            if (ModelState.IsValid)
            {
                db.Entry(delivery).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(delivery);
        }

        [HttpGet]
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Delivery delivery = db.Delivery.Find(id);
            if (delivery == null)
            {
                return HttpNotFound();
            }
            return View(delivery);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Delivery delivery = db.Delivery.Find(id);
            db.Delivery.Remove(delivery);
            db.SaveChanges();
            return RedirectToAction("Index");
        }
    }
}