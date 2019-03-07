using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Models;

namespace DelControlWeb.Controllers
{
    public class OrderStatusController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        public ActionResult Index()
        {
            return View(db.OrderStatuses.ToList());
        }

        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            OrderStatus orderStatus = db.OrderStatuses.Find(id);
            if (orderStatus == null)
            {
                return HttpNotFound();
            }
            return View(orderStatus);
        }

        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Id,Name")] OrderStatus orderStatus)
        {
            if (ModelState.IsValid)
            {
                db.OrderStatuses.Add(orderStatus);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(orderStatus);
        }

        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            OrderStatus orderStatus = db.OrderStatuses.Find(id);
            if (orderStatus == null)
            {
                return HttpNotFound();
            }
            return View(orderStatus);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Id,Name")] OrderStatus orderStatus)
        {
            if (ModelState.IsValid)
            {
                db.Entry(orderStatus).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(orderStatus);
        }

        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            OrderStatus orderStatus = db.OrderStatuses.Find(id);
            if (orderStatus == null)
            {
                return HttpNotFound();
            }
            return View(orderStatus);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            OrderStatus orderStatus = db.OrderStatuses.Find(id);
            db.OrderStatuses.Remove(orderStatus);
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