using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Models;

namespace DelControlWeb.Controllers
{
    public class CourierStatusController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        public ActionResult Index()
        {
            return View(db.CourierStatuses.ToList());
        }

        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CourierStatus courierStatus = db.CourierStatuses.Find(id);
            if (courierStatus == null)
            {
                return HttpNotFound();
            }
            return View(courierStatus);
        }

        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Id,Name")] CourierStatus courierStatus)
        {
            if (ModelState.IsValid)
            {
                db.CourierStatuses.Add(courierStatus);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(courierStatus);
        }

        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CourierStatus courierStatus = db.CourierStatuses.Find(id);
            if (courierStatus == null)
            {
                return HttpNotFound();
            }
            return View(courierStatus);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Id,Name")] CourierStatus courierStatus)
        {
            if (ModelState.IsValid)
            {
                db.Entry(courierStatus).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(courierStatus);
        }

        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CourierStatus courierStatus = db.CourierStatuses.Find(id);
            if (courierStatus == null)
            {
                return HttpNotFound();
            }
            return View(courierStatus);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            CourierStatus courierStatus = db.CourierStatuses.Find(id);
            db.CourierStatuses.Remove(courierStatus);
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