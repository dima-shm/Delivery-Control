﻿using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DelControlWeb.Models
{
    public class CourierInfo
    {
        [Key]
        public string CourierId { get; set; }
        [ForeignKey("CourierId")]
        private User User { get; set; }

        public float Latitude { get; set; }

        public float Longitude { get; set; }

        public float Speed { get; set; }

        public DateTime Time { get; set; }

        public string Status { get; set; }
    }
}