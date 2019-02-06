using System;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.Models
{
    public class Company
    {
        [Key]
        public int Id { get; set; }

        public string Name { get; set; }

        public string Address { get; set; }

        public string RegisterNumber { get; set; }

        public DateTime RegisterDate { get; set; }
    }
}