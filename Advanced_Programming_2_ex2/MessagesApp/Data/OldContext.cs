/*
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using MessagesApp.Models;

namespace MessagesApp.Data
{
    public class OldContext : DbContext
    {
        public OldContext() : base(GetOptions("server=localhost/3306;database=MessagesApp;user=root;password=1234"))
        {
        }

        public OldContext(DbContextOptions<OldContext> dbContext) : base(dbContext) { }

        private static DbContextOptions GetOptions(string connectionString)
        {
            return SqlServerDbContextOptionsExtensions.UseSqlServer(new DbContextOptionsBuilder(), connectionString).Options;
        }

        private const string connectionString = "server=localhost;port=3306;database=MessagesApp;user=root;password=1234";

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configuring the Name property as the primary
            // key of the Items table
            modelBuilder.Entity<User>().HasKey(e => e.Username);
            modelBuilder.Entity<Rate>().HasKey(e => e.Id);
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Rate> Rates { get; set; }
    }
}
*/