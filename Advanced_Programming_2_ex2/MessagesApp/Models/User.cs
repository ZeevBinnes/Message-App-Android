using System.ComponentModel.DataAnnotations;

namespace MessagesApp.Models
{
    public class User
    {
        [Key, Required]
        public string Userid { get; set; }

        [Required]
        public string Password { get; set; }

        public string Picture { get; set; }

    }
}