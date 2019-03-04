using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class LastCourierLocation
    {
        [Key]
        public int Id { get; set; }

        public int CourierId { get; set; }
        [ForeignKey("CourierId")]
        private User User { get; set; }

        public float Latitude { get; set; }

        public float Longitude { get; set; }

        public float Speed { get; set; }

        public DateTime Time { get; set; }
    }
}