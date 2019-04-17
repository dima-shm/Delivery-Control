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
        private ApplicationContext db = new ApplicationContext();

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
            return View(db.Orders.ToList());
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
            List<Product> products = new List<Product>();
            foreach (OrderProducts orderProduct in orderProducts)
            {
                products.Add(db.Products.First(p => p.Id == orderProduct.ProductId));
            }
            DetailsViewModel model = new DetailsViewModel
            {
                CustomerId = order.CustomerId,
                CustomerName = db.Customers.Find(order.CustomerId).Name,
                DeliveryId = order.DeliveryId,
                Delivery = db.Delivery.Find(order.DeliveryId).Address,
                CourierId = order.CourierId,
                Products = products,
                Status = order.Status
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
            ViewBag.Customers = db.Customers;
            ViewBag.Delivery = db.Delivery;
            ViewBag.Products = db.Products;
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create(CreateViewModel model)
        {
            if (ModelState.IsValid)
            {
                User currentUser = UserManager.FindById(User.Identity.GetUserId());
                Order order = new Order
                {
                    CompanyId = currentUser.CompanyId,
                    CustomerId = model.CustomerId,
                    DeliveryId = model.DeliveryId
                };
                db.Orders.Add(order);
                await db.SaveChangesAsync();
                foreach (int productId in model.ProductIds)
                {
                    OrderProducts orderProducts = new OrderProducts
                    {
                        OrderId = order.Id,
                        ProductId = productId
                    };
                    db.OrderProducts.Add(orderProducts);
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
            if (order == null)
            {
                return HttpNotFound();
            }
            return View(order);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(Order order)
        {
            if (ModelState.IsValid)
            {
                db.Entry(order).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(order);
        }

        [HttpGet]
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Order order = db.Orders.Find(id);
            if (order == null)
            {
                return HttpNotFound();
            }
            return View(order);
        }

        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Order order = db.Orders.Find(id);
            db.Orders.Remove(order);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        [HttpGet]
        public ActionResult ProductsDetails(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            List<OrderProducts> orderProducts = db.OrderProducts.Where(p => p.OrderId == id).ToList();
            List<Product> products = new List<Product>();
            foreach (OrderProducts orderProduct in orderProducts)
            {
                products.Add(db.Products.First(p => p.Id == orderProduct.ProductId));
            }
            if (orderProducts == null)
            {
                return HttpNotFound();
            }
            return View(products);
        }
    }
}