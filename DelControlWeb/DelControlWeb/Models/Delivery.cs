using System;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.Models
{
    public class Delivery
    {
        [Key]
        public int Id { get; set; }

        public string Address { get; set; }

        public DateTime Date { get; set; }

        public DateTime Time { get; set; }
    }
}