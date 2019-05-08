using System;
using System.Collections.Generic;
using System.Data.Entity;
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

        // GET: api/CourierInfo
        public IHttpActionResult GetCourierInfo()
        {
            List<CourierInfo> couriers = new List<CourierInfo>();
            couriers.Add(new CourierInfo()
            {
                CourierId = "Иванов И.И.",
                Latitude = 53.90F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Петров П.П.",
                Latitude = 53.90F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Сидоров А.А.",
                Latitude = 53.90F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Пупкин О.П.",
                Latitude = 53.90F,
                Longitude = 27.44F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Моник А.П.",
                Latitude = 53.90F,
                Longitude = 27.57F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Лунев Д.В.",
                Latitude = 53.90F,
                Longitude = 27.26F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Потапов В.В.",
                Latitude = 53.50F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Крутько А.Д.",
                Latitude = 53.93F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            return Ok(couriers);
            //return db.CourierInfo;
        }

        // GET: api/CourierInfo/5
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult GetCourierInfo(string id)
        {
            CourierInfo courierInfo = db.CourierInfoes.First(c => c.CourierId == id);
            if (courierInfo == null)
            {
                return NotFound();
            }
            return Ok(courierInfo);
        }

        // PUT: api/CourierInfo/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCourierInfo(CourierInfo courierInfo)
        {
            if(!CourierInfoExists(courierInfo.CourierId))
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