using System.ComponentModel.DataAnnotations;

namespace MessagesApp.Models
{
    public class User
    {
        [Key, Required]
        public string Username { get; set; }

        public List<Contact> Contacts { get; set; }

        [Required]
        public string Password { get; set; }

        public string Picture { get; set; }

        public User()
        {
            Contacts = new List<Contact>();
            Picture = null;
        }

    }
}