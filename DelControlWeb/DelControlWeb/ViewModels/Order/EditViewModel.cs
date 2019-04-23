using DelControlWeb.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.Order
{
    public class EditViewModel
    {
        public int OrderId { get; set; }

        public string CustomerName { get; set; }

        public string DeliveryAddress { get; set; }

        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:yyyy-MM-dd}", ApplyFormatInEditMode = true)]
        public DateTime DeliveryDate { get; set; }

        [DataType(DataType.Time)]
        public DateTime DeliveryTime { get; set; }

        public string Comment { get; set; }

        public List<OrderProducts> OrderProducts { get; set; }
    }
}