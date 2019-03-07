using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Models;

namespace DelControlWeb.Controllers
{
    public class CompanyCourierStatusesController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        public ActionResult Index()
        {
            return View(db.CompanyCourierStatuses.ToList());
        }

        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CompanyCourierStatuses companyCourierStatuses = db.CompanyCourierStatuses.Find(id);
            if (companyCourierStatuses == null)
            {
                return HttpNotFound();
            }
            return View(companyCourierStatuses);
        }

        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Id,CompanyId,CourierStatusId")] CompanyCourierStatuses companyCourierStatuses)
        {
            if (ModelState.IsValid)
            {
                db.CompanyCourierStatuses.Add(companyCourierStatuses);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(companyCourierStatuses);
        }

        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CompanyCourierStatuses companyCourierStatuses = db.CompanyCourierStatuses.Find(id);
            if (companyCourierStatuses == null)
            {
                return HttpNotFound();
            }
            return View(companyCourierStatuses);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Id,CompanyId,CourierStatusId")] CompanyCourierStatuses companyCourierStatuses)
        {
            if (ModelState.IsValid)
            {
                db.Entry(companyCourierStatuses).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(companyCourierStatuses);
        }

        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CompanyCourierStatuses companyCourierStatuses = db.CompanyCourierStatuses.Find(id);
            if (companyCourierStatuses == null)
            {
                return HttpNotFound();
            }
            return View(companyCourierStatuses);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            CompanyCourierStatuses companyCourierStatuses = db.CompanyCourierStatuses.Find(id);
            db.CompanyCourierStatuses.Remove(companyCourierStatuses);
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