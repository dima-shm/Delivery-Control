using System;
namespace DelControlWeb.ViewModels.CourierInfoes
{
    public class CourierInfo
    {
        public string Id { get; set; }

        public string Name { get; set; }

        public float Latitude { get; set; }

        public float Longitude { get; set; }

        public float Speed { get; set; }

        public DateTime Time { get; set; }

        public string Status { get; set; }
    }
}