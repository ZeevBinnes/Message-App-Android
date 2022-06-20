namespace MessagesApp.Models
{
    public class JsonContact
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Server { get; set; }
        public string Last { get; set; }
        public DateTime Lastdate { get; set; }

        public static JsonContact GetJsonContact(Contact contact)
        {
            JsonContact contact_json = new JsonContact();
            contact_json.Id = contact.Contactid;
            contact_json.Name = contact.Nickname;
            contact_json.Server = contact.Server;
            contact_json.Last = contact.LastContent;
            contact_json.Lastdate = contact.LastDate;
            return contact_json;
        }
    }
}
