using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Models;

namespace DelControlWeb.Controllers
{
    public class OrderProductsController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        public ActionResult Index()
        {
            return View(db.OrderProducts.ToList());
        }

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

        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Id,OrderId,ProductId")] OrderProducts orderProducts)
        {
            if (ModelState.IsValid)
            {
                db.OrderProducts.Add(orderProducts);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(orderProducts);
        }

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
        public ActionResult Edit([Bind(Include = "Id,OrderId,ProductId")] OrderProducts orderProducts)
        {
            if (ModelState.IsValid)
            {
                db.Entry(orderProducts).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(orderProducts);
        }

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

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}