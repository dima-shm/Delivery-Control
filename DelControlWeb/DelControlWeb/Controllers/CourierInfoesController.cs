using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;
using DelControlWeb.Context;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class CourierInfoesController : ApiController
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

        // GET: api/CourierInfo/5
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult GetCourierInfo(string id)
        {
            int companyId = int.Parse(id);
            List<User> users = db.Users.Where(u => u.CompanyId == companyId).ToList();
            List<CourierInfo> couriers = new List<CourierInfo>();
            foreach (User user in users)
                foreach (CourierInfo courierInfo in db.CourierInfoes.Where(c => c.CourierId == user.Id))
                    couriers.Add(courierInfo);
            List<ViewModels.CourierInfoes.CourierInfo> couriersViewModel =
                new List<ViewModels.CourierInfoes.CourierInfo>();
            foreach (CourierInfo courier in couriers)
            {
                couriersViewModel.Add(new ViewModels.CourierInfoes.CourierInfo()
                {
                    Id = courier.CourierId,
                    Name = db.Users.Find(courier.CourierId).UserName,
                    Latitude = courier.Latitude,
                    Longitude = courier.Longitude,
                    Speed = courier.Speed,
                    Time = courier.Time,
                    Status = courier.Status,
                });
            }
            return Ok(couriersViewModel);
        }

        // PUT: api/CourierInfo/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCourierInfo(CourierInfo courierInfo)
        {
            if (!CourierInfoExists(courierInfo.CourierId))
            {
                return PostCourierInfo(courierInfo);
            }
            CourierInfo currentCourierInfo = db.CourierInfoes.Find(courierInfo.CourierId);
            currentCourierInfo.Time = DateTime.Now;
            currentCourierInfo.Latitude = courierInfo.Latitude == 0 ?
                currentCourierInfo.Latitude :
                courierInfo.Latitude;
            currentCourierInfo.Longitude = courierInfo.Longitude == 0 ?
                currentCourierInfo.Longitude :
                courierInfo.Longitude;
            currentCourierInfo.Speed = courierInfo.Speed;
            currentCourierInfo.Status = courierInfo.Status == null ?
                currentCourierInfo.Status :
                courierInfo.Status;
            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CourierInfoExists(courierInfo.CourierId))
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

        // POST: api/CourierInfo
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult PostCourierInfo(CourierInfo courierInfo)
        {
            courierInfo.Time = DateTime.Now;
            db.CourierInfoes.Add(courierInfo);
            db.SaveChanges();
            return CreatedAtRoute("DefaultApi", new { id = courierInfo.CourierId }, courierInfo);
        }

        // DELETE: api/CourierInfo/5
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult DeleteCourierInfo(int id)
        {
            CourierInfo courierInfo = db.CourierInfoes.Find(id);
            if (courierInfo == null)
            {
                return NotFound();
            }
            db.CourierInfoes.Remove(courierInfo);
            db.SaveChanges();
            return Ok(courierInfo);
        }

        private bool CourierInfoExists(string id)
        {
            return db.CourierInfoes.Count(e => e.CourierId == id) > 0;
        }
    }
}