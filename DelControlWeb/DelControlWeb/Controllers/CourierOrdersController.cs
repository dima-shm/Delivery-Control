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
            List<Order> orders = db.Orders.Where(o => o.CourierId == id &&
                (!o.Status.Equals("Оплачен") || o.Status.Equals("Отменен"))).ToList();
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

        // PUT: api/CourierOrders/id/status
        [ResponseType(typeof(void))]
        [Route("api/CourierOrders/{id}/{status}")]
        public IHttpActionResult PutOrder(int id, string status)
        {
            Order order = db.Orders.Find(id);
            order.Status = status;
            db.SaveChanges();
            return StatusCode(HttpStatusCode.NoContent);
        }
    }
}