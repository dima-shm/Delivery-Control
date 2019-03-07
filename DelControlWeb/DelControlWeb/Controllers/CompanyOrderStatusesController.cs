using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Models;

namespace DelControlWeb.Controllers
{
    public class CompanyOrderStatusesController : Controller
    {
        private ApplicationContext db = new ApplicationContext();

        public ActionResult Index()
        {
            return View(db.CompanyOrderStatuses.ToList());
        }

        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CompanyOrderStatuses companyOrderStatuses = db.CompanyOrderStatuses.Find(id);
            if (companyOrderStatuses == null)
            {
                return HttpNotFound();
            }
            return View(companyOrderStatuses);
        }

        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Id,CompanyId,OrderStatusId")] CompanyOrderStatuses companyOrderStatuses)
        {
            if (ModelState.IsValid)
            {
                db.CompanyOrderStatuses.Add(companyOrderStatuses);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(companyOrderStatuses);
        }

        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CompanyOrderStatuses companyOrderStatuses = db.CompanyOrderStatuses.Find(id);
            if (companyOrderStatuses == null)
            {
                return HttpNotFound();
            }
            return View(companyOrderStatuses);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Id,CompanyId,OrderStatusId")] CompanyOrderStatuses companyOrderStatuses)
        {
            if (ModelState.IsValid)
            {
                db.Entry(companyOrderStatuses).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(companyOrderStatuses);
        }

        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            CompanyOrderStatuses companyOrderStatuses = db.CompanyOrderStatuses.Find(id);
            if (companyOrderStatuses == null)
            {
                return HttpNotFound();
            }
            return View(companyOrderStatuses);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            CompanyOrderStatuses companyOrderStatuses = db.CompanyOrderStatuses.Find(id);
            db.CompanyOrderStatuses.Remove(companyOrderStatuses);
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