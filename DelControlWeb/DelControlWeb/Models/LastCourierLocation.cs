using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class LastCourierLocation
    {
        [Key]
        public int Id { get; set; }

        public int? CourierId { get; set; }
        [ForeignKey("CourierId")]
        private User User { get; set; }

        private float Latitude { get; set; }

        private float Longitude { get; set; }

        private float Speed { get; set; }

        private DateTime Time { get; set; }
    }
}