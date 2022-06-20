using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace MessagesApp.Models
{
    public class Message
    {
        public int Id { get; set; }

        
        public string Userid { get; set; }

        public string Contactid { get; set; }

        public bool Sent { get; set; }

        public DateTime Time { get; set; }


        public string Content { get; set; }

       }
}