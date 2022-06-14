using System.ComponentModel.DataAnnotations;

namespace MessagesApp.Models
{
    public class Rate
    {
        public int Id { get; set; }

        [Display(Name = "Rate")]
        [Required]
        [Range(1, 5)]
        public int NumRate { get; set; }

        public string? Feedback { get; set; }

        public DateTime Time { get; set; }

        [Display(Name = "Rater's name")]
        [Required]
        public string RaterName { get; set; }
    }
}
