using System.ComponentModel.DataAnnotations;

namespace MessagesApp.Models
{
    public class Message
    {
        public int Id { get; set; }

        //[Required]
        //public User From { get; set; }

        //[Required]
        //public User To { get; set; }

        [Required]
        public bool Sent { get; set; }

        [Required]
        public DateTime Time { get; set; }


        [Required]
        public string Content { get; set; }


        [Required]
        public string Type { get; set; }

    }
}