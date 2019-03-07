using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web.Http;
using System.Web.Http.Description;
using DelControlWeb.Context;
using DelControlWeb.Models;

namespace DelControlWeb.Controllers
{
    public class LastCourierLocationsController : ApiController
    {
        private ApplicationContext db = new ApplicationContext();

        // GET: api/LastCourierLocations
        public IQueryable<LastCourierLocation> GetLastCourierLocations()
        {
            return db.LastCourierLocations;
        }

        // GET: api/LastCourierLocations/5
        [ResponseType(typeof(LastCourierLocation))]
        public IHttpActionResult GetLastCourierLocation(int id)
        {
            LastCourierLocation lastCourierLocation = db.LastCourierLocations.Find(id);
            if (lastCourierLocation == null)
            {
                return NotFound();
            }
            return Ok(lastCourierLocation);
        }

        // PUT: api/LastCourierLocations/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutLastCourierLocation(int id, LastCourierLocation lastCourierLocation)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if (id != lastCourierLocation.Id)
            {
                return BadRequest();
            }
            db.Entry(lastCourierLocation).State = EntityState.Modified;
            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!LastCourierLocationExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/LastCourierLocations
        [ResponseType(typeof(LastCourierLocation))]
        public IHttpActionResult PostLastCourierLocation(LastCourierLocation lastCourierLocation)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            db.LastCourierLocations.Add(lastCourierLocation);
            db.SaveChanges();
            return CreatedAtRoute("DefaultApi", new { id = lastCourierLocation.Id }, lastCourierLocation);
        }

        // DELETE: api/LastCourierLocations/5
        [ResponseType(typeof(LastCourierLocation))]
        public IHttpActionResult DeleteLastCourierLocation(int id)
        {
            LastCourierLocation lastCourierLocation = db.LastCourierLocations.Find(id);
            if (lastCourierLocation == null)
            {
                return NotFound();
            }
            db.LastCourierLocations.Remove(lastCourierLocation);
            db.SaveChanges();
            return Ok(lastCourierLocation);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool LastCourierLocationExists(int id)
        {
            return db.LastCourierLocations.Count(e => e.Id == id) > 0;
        }
    }
}