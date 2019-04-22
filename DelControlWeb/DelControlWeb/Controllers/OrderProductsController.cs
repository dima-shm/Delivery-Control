using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class OrderProductsController : Controller
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

        [HttpGet]
        public ActionResult Index()
        {
            return View(db.OrderProducts.ToList());
        }

        [HttpGet]
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            OrderProducts orderProducts = db.OrderProducts.Find(id);
            if (orderProducts == null)
            {
                return HttpNotFound();
            }
            return View(orderProducts);
        }

        [HttpGet]
        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(OrderProducts orderProducts)
        {
            if (ModelState.IsValid)
            {
                db.OrderProducts.Add(orderProducts);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(orderProducts);
        }

        [HttpGet]
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            OrderProducts orderProducts = db.OrderProducts.Find(id);
            if (orderProducts == null)
            {
                return HttpNotFound();
            }
            return View(orderProducts);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(OrderProducts orderProducts)
        {
            if (ModelState.IsValid)
            {
                db.Entry(orderProducts).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(orderProducts);
        }

        [HttpGet]
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            OrderProducts orderProducts = db.OrderProducts.Find(id);
            if (orderProducts == null)
            {
                return HttpNotFound();
            }
            return View(orderProducts);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            OrderProducts orderProducts = db.OrderProducts.Find(id);
            db.OrderProducts.Remove(orderProducts);
            db.SaveChanges();
            return RedirectToAction("Index");
        }
    }
}