using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web.Http;
using System.Web.Http.Description;
using DelControlWeb.Context;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.CourierOrders;

namespace DelControlWeb.Controllers
{
    public class CourierOrdersController : ApiController
    {
        private ApplicationContext db = new ApplicationContext();

        // GET: api/CourierOrders/5
        [ResponseType(typeof(Order))]
        public IHttpActionResult GetOrder(string id)
        {
            List<CourierOrder> courierOrders = new List<CourierOrder>();
            List<Order> orders = db.Orders.Where(o => o.CourierId == id).ToList();
            foreach(Order order in orders)
            {
                courierOrders.Add(new CourierOrder
                {
                    Id = order.Id,
                    CompanyId = order.CompanyId,
                    CustomerName = order.CustomerName,
                    DeliveryAddress = order.DeliveryAddress,
                    DeliveryDate = order.DeliveryDate.ToString("d"),
                    DeliveryTime = order.DeliveryTime.ToString("t"),
                    Comment = order.Comment,
                    CourierId = order.CourierId,
                    Status = order.Status,
                    OrderProducts = db.OrderProducts.Where(o => o.OrderId == order.Id).ToList()
                });
            }
            return Ok(courierOrders);
        }

        // PUT: api/CourierOrders/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutOrder(int id, Order order)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != order.Id)
            {
                return BadRequest();
            }

            db.Entry(order).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!OrderExists(id))
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

        // POST: api/CourierOrders
        [ResponseType(typeof(Order))]
        public IHttpActionResult PostOrder(Order order)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Orders.Add(order);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = order.Id }, order);
        }

        // DELETE: api/CourierOrders/5
        [ResponseType(typeof(Order))]
        public IHttpActionResult DeleteOrder(int id)
        {
            Order order = db.Orders.Find(id);
            if (order == null)
            {
                return NotFound();
            }

            db.Orders.Remove(order);
            db.SaveChanges();

            return Ok(order);
        }

        private bool OrderExists(int id)
        {
            return db.Orders.Count(e => e.Id == id) > 0;
        }
    }
}