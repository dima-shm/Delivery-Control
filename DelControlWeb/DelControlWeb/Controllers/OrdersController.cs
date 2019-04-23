using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.Order;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class OrdersController : Controller
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

        private ApplicationUserManager UserManager
        {
            get
            {
                return HttpContext.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
        }

        [HttpGet]
        public ActionResult Index()
        {
            User currentUser = UserManager.FindById(User.Identity.GetUserId());
            List<Order> orders = db.Orders.Where(o => o.CompanyId == currentUser.CompanyId).ToList();
            return View(orders);
        }

        [HttpGet]
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Order order = db.Orders.Find(id);
            List<OrderProducts> orderProducts = db.OrderProducts.Where(p => p.OrderId == id).ToList();
            OrderViewModel model = new OrderViewModel
            {
                OrderId = order.Id,
                CompanyId = order.CompanyId,
                CustomerName = order.CustomerName,
                DeliveryAddress = order.DeliveryAddress,
                DeliveryDate = order.DeliveryDate,
                DeliveryTime = order.DeliveryTime,
                Comment = order.Comment,
                CourierId = order.CourierId,
                Status = order.Status,
                OrderProducts = orderProducts
            };
            if (order == null)
            {
                return HttpNotFound();
            }
            return View(model);
        }

        [HttpGet]
        public ActionResult Create()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create(CreateViewModel model)
        {
            ModelState.Remove("Commnet");
            if (ModelState.IsValid)
            {
                User currentUser = UserManager.FindById(User.Identity.GetUserId());
                Order order = new Order
                {
                    CompanyId = currentUser.CompanyId,
                    CustomerName = model.CustomerName,
                    DeliveryAddress = model.DeliveryAddress,
                    DeliveryDate = model.DeliveryDate,
                    DeliveryTime = model.DeliveryDate
                        .Add(new TimeSpan(0, model.DeliveryTime.Hour, model.DeliveryTime.Minute, 0)),
                    Comment = model.Comment
                };
                db.Orders.Add(order);
                await db.SaveChangesAsync();
                foreach (OrderProducts product in model.OrderProducts)
                {
                    product.OrderId = order.Id;
                    db.OrderProducts.Add(product);
                    await db.SaveChangesAsync();
                }
                return RedirectToAction("Index");
            }
            return View(model);
        }

        [HttpGet]
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Order order = db.Orders.Find(id);
            List<OrderProducts> orderProducts = db.OrderProducts.Where(p => p.OrderId == id).ToList();
            EditViewModel model = new EditViewModel
            {
                OrderId = order.Id,
                CustomerName = order.CustomerName,
                DeliveryAddress = order.DeliveryAddress,
                DeliveryDate = order.DeliveryDate,
                DeliveryTime = order.DeliveryTime,
                Comment = order.Comment,
                OrderProducts = orderProducts
            };
            if (order == null)
            {
                return HttpNotFound();
            }
            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit(EditViewModel model)
        {
            ModelState.Remove("Commnet");
            if (ModelState.IsValid)
            {
                User currentUser = UserManager.FindById(User.Identity.GetUserId());
                Order order = new Order
                {
                    Id = model.OrderId,
                    CompanyId = currentUser.CompanyId,
                    CustomerName = model.CustomerName,
                    DeliveryAddress = model.DeliveryAddress,
                    DeliveryDate = model.DeliveryDate,
                    DeliveryTime = model.DeliveryDate
                        .Add(new TimeSpan(0, model.DeliveryTime.Hour, model.DeliveryTime.Minute, 0)),
                    Comment = model.Comment
                };
                db.Entry(order).State = EntityState.Modified;
                await db.SaveChangesAsync();
                db.OrderProducts.RemoveRange(db.OrderProducts.Where(o => o.OrderId == order.Id));
                await db.SaveChangesAsync();
                foreach (OrderProducts product in model.OrderProducts)
                {
                    db.OrderProducts.Add(product);
                    await db.SaveChangesAsync();
                }
                return RedirectToAction("Index");
            }
            return View(model);
        }

        [HttpGet]
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Order order = db.Orders.Find(id);
            List<OrderProducts> orderProducts = db.OrderProducts.Where(p => p.OrderId == id).ToList();
            OrderViewModel model = new OrderViewModel
            {
                CompanyId = order.CompanyId,
                CustomerName = order.CustomerName,
                DeliveryAddress = order.DeliveryAddress,
                DeliveryTime = order.DeliveryTime,
                Comment = order.Comment,
                CourierId = order.CourierId,
                Status = order.Status,
                OrderProducts = orderProducts
            };
            if (order == null)
            {
                return HttpNotFound();
            }
            return View(model);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            List<OrderProducts> orderProducts = db.OrderProducts.Where(p => p.OrderId == id).ToList();
            foreach (OrderProducts orderProduct in orderProducts)
            {
                db.OrderProducts.Remove(orderProduct);
                db.SaveChanges();
            }
            Order order = db.Orders.Find(id);
            db.Orders.Remove(order);
            db.SaveChanges();
            return RedirectToAction("Index");
        }
    }
}