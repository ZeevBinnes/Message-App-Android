using System.ComponentModel.DataAnnotations;

namespace MessagesApp.Models
{
    public class Contact
    {
        [Key]
        public string User { get; set; }

        public string Nickname { get; set; }

        public List<Message> Messages { get; set; }

        [Required]
        public string Server { get; set; }

        public Contact()
        {
            Messages = new List<Message>();
        }

    }
}
