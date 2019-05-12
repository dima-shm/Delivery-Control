using DelControlWeb.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.CourierOrders
{
    public class CourierOrder
    {
        public int Id { get; set; }

        public int? CompanyId { get; set; }

        public string CustomerName { get; set; }

        public string DeliveryAddress { get; set; }

        public string DeliveryDate { get; set; }

        public string DeliveryTime { get; set; }

        public string Comment { get; set; }

        public string CourierId { get; set; }

        public string Status { get; set; }

        public List<OrderProducts> OrderProducts { get; set; }
    }
}