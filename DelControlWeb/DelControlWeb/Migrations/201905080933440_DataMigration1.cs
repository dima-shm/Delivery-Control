namespace DelControlWeb.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class DataMigration1 : DbMigration
    {
        public override void Up()
        {
            DropPrimaryKey("dbo.CourierInfoes");
            AddColumn("dbo.CourierInfoes", "CourierId", c => c.String(nullable: false, maxLength: 128));
            AddPrimaryKey("dbo.CourierInfoes", "CourierId");
            CreateIndex("dbo.CourierInfoes", "CourierId");
            AddForeignKey("dbo.CourierInfoes", "CourierId", "dbo.AspNetUsers", "Id");
            DropColumn("dbo.CourierInfoes", "Id");
            DropColumn("dbo.CourierInfoes", "Name");
        }
        
        public override void Down()
        {
            AddColumn("dbo.CourierInfoes", "Name", c => c.String());
            AddColumn("dbo.CourierInfoes", "Id", c => c.Int(nullable: false, identity: true));
            DropForeignKey("dbo.CourierInfoes", "CourierId", "dbo.AspNetUsers");
            DropIndex("dbo.CourierInfoes", new[] { "CourierId" });
            DropPrimaryKey("dbo.CourierInfoes");
            DropColumn("dbo.CourierInfoes", "CourierId");
            AddPrimaryKey("dbo.CourierInfoes", "Id");
        }
    }
}
