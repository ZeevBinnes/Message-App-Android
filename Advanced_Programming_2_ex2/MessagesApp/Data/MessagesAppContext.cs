using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using MessagesApp.Models;

namespace MessagesApp.Data
{
    public class MessagesAppContext : DbContext
    {
        public MessagesAppContext(DbContextOptions<MessagesAppContext> options)
            : base(options)
        {
        }

        public DbSet<MessagesApp.Models.User> Users { get; set; }

        public DbSet<MessagesApp.Models.Message> Messages { get; set; }

        public DbSet<MessagesApp.Models.Contact> Contacts { get; set; }

    }
}