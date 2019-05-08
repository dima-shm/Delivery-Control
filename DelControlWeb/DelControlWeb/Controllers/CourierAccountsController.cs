using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;
using DelControlWeb.Context;
using DelControlWeb.Managers;
using DelControlWeb.Models;
using DelControlWeb.ViewModels.CourierAccounts;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class CourierAccountsController : ApiController
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

        private ApplicationUserManager UserManager
        {
            get
            {
                var manager = new ApplicationUserManager(
                    new Microsoft.AspNet.Identity.EntityFramework.UserStore<User>(db));
                manager.UserValidator = new UserValidator<User>(manager)
                {
                    AllowOnlyAlphanumericUserNames = false,
                    RequireUniqueEmail = false
                };
                return manager;
                    
            }
        }

        // GET: api/CourierAccounts
        public IEnumerable<CourierAccountViewModel> GetUsers()
        {
            List<CourierAccountViewModel> couriers = new List<CourierAccountViewModel>();
            foreach(User user in db.Users)
            {
                couriers.Add(new CourierAccountViewModel()
                {
                    Id = user.Id,
                    CompanyId = user.CompanyId,
                    Name = user.UserName,
                    Email = user.Email,
                    Phone = user.Phone,
                    Address = user.Address
                });
            }
            return couriers;
        }

        // GET: api/CourierAccounts/email/password
        [ResponseType(typeof(CourierAccountViewModel))]
        [Route("api/CourierAccounts/{email}/{password}")]
        public IHttpActionResult GetUser(string email, string password)
        {
            User user = UserManager.FindByEmail(email);
            if (user != null)
            {
                user = UserManager.Find(user.UserName, password);
            }
            if (user == null)
            {
                return NotFound();
            }
            CourierAccountViewModel courier = new CourierAccountViewModel()
            {
                Id = user.Id,
                CompanyId = user.CompanyId,
                Name = user.UserName,
                Email = user.Email,
                Phone = user.Phone,
                Address = user.Address
            };
            return Ok(courier);
        }

        // POST: api/CourierAccounts
        [ResponseType(typeof(User))]
        public IHttpActionResult PostUser(CourierAccountViewModel courier)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            User user = new User
            {
                CompanyId = courier.CompanyId,
                UserName = courier.Name,
                Phone = courier.Phone,
                Address = courier.Address,
                Email = courier.Email,
            };
            IdentityResult result = CreateAcount(user, courier.Password, "courier");
            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (UserExists(user.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }
            courier.Id = user.Id;
            return CreatedAtRoute("DefaultApi", new { id = user.Id }, courier);
        }

        // PUT: api/CourierAccounts/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutUser(string id, CourierAccountViewModel courier)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            User user = db.Users.First(u => u.Email == id);
            user.UserName = courier.Name;
            user.Email = courier.Email;
            user.Phone = courier.Phone;
            user.Address = courier.Address;
            db.Entry(user).State = EntityState.Modified;
            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
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

        // DELETE: api/CourierAccounts/5
        [ResponseType(typeof(User))]
        public IHttpActionResult DeleteUser(string id)
        {
            User user = db.Users.First(u => u.Email == id);
            if (user == null)
            {
                return NotFound();
            }
            db.Users.Remove(user);
            db.SaveChanges();
            return Ok(user);
        }

        private bool UserExists(string id)
        {
            return db.Users.Count(e => e.Id == id) > 0;
        }

        private IdentityResult CreateAcount(User user, string userPassword, string roleName)
        {
            IdentityResult result = UserManager.Create(user, userPassword);
            if (result.Succeeded)
            {
                UserManager.AddToRole(user.Id, roleName);
            }
            return result;
        }
    }
}