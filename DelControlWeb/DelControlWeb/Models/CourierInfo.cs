using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class CourierInfo
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public float Latitude { get; set; }

        public float Longitude { get; set; }

        public float Speed { get; set; }

        public DateTime Time { get; set; }

        public string Status { get; set; }
    }
}