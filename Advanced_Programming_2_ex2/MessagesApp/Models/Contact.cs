using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MessagesApp.Models
{
    public class Contact
    {

        public string Userid { get; set; }

        public string Contactid { get; set; }
        
        public string Nickname { get; set; }

        public string LastContent { get; set; }
        public DateTime LastDate { get; set; }

        [Required]
        public string Server { get; set; }

    }
}
