namespace MessagesApp.Models
{
    public class JsonMessage
    {
        public int? Id { get; set; }
        //[JsonProperty("id")]
        public string Content { get; set; }
        public DateTime Created { get; set; }
        public bool Sent { get; set; }

        public static JsonMessage GetJsonMessage(Message message)
        {
            JsonMessage message_json = new JsonMessage();
            message_json.Id = message.Id;
            message_json.Content = message.Content;
            message_json.Created = message.Time;
            message_json.Sent = message.Sent;

            return message_json;
        }
    }
}
