namespace DelControlWeb.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class UpdateUserMigration : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.AspNetUsers", "Surname");
            DropColumn("dbo.AspNetUsers", "Patronymic");
        }
        
        public override void Down()
        {
            AddColumn("dbo.AspNetUsers", "Patronymic", c => c.String());
            AddColumn("dbo.AspNetUsers", "Surname", c => c.String());
        }
    }
}
