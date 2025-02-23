﻿using Microsoft.AspNet.Identity.EntityFramework;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class User : IdentityUser
    {
        public int? CompanyId { get; set; }
        [ForeignKey("CompanyId")]
        private Company Company { get; set; }

        public string Phone { get; set; }

        public string Address { get; set; }

        public string Status { get; set; }
    }
}