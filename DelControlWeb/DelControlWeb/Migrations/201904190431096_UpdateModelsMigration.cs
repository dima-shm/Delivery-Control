namespace DelControlWeb.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UpdateModelsMigration : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Customers", "CompanyId", c => c.Int());
            AddColumn("dbo.Deliveries", "CompanyId", c => c.Int());
            AddColumn("dbo.Products", "CompanyId", c => c.Int());
            CreateIndex("dbo.Customers", "CompanyId");
            CreateIndex("dbo.Deliveries", "CompanyId");
            CreateIndex("dbo.Products", "CompanyId");
            AddForeignKey("dbo.Customers", "CompanyId", "dbo.Companies", "Id");
            AddForeignKey("dbo.Deliveries", "CompanyId", "dbo.Companies", "Id");
            AddForeignKey("dbo.Products", "CompanyId", "dbo.Companies", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Products", "CompanyId", "dbo.Companies");
            DropForeignKey("dbo.Deliveries", "CompanyId", "dbo.Companies");
            DropForeignKey("dbo.Customers", "CompanyId", "dbo.Companies");
            DropIndex("dbo.Products", new[] { "CompanyId" });
            DropIndex("dbo.Deliveries", new[] { "CompanyId" });
            DropIndex("dbo.Customers", new[] { "CompanyId" });
            DropColumn("dbo.Products", "CompanyId");
            DropColumn("dbo.Deliveries", "CompanyId");
            DropColumn("dbo.Customers", "CompanyId");
        }
    }
}
