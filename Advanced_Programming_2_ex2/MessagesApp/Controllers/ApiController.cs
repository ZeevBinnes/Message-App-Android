using Microsoft.AspNetCore.Mvc;
using MessagesApp.Services;
using Microsoft.AspNetCore.SignalR;
using Microsoft.AspNetCore.Cors;
using MessagesApp.Hubs;

namespace MessagesApp.Controllers
{
    [Route("api")]
    [ApiController]
    public class MainController : ControllerBase
    {
        private readonly IHubContext<ChatHub> hub;
        private static AppService service = AppService.CreateAppService();

        public MainController(IHubContext<ChatHub> hubContext)
        {
            this.hub = hubContext;
        }

        public class LoginFormat
        {
            public string id { get; set; }
            public string pass { get; set; }
        }
        public class ApiFormat
        {
            public string? from { get; set; }
            public string? to { get; set; }
            public string? content { get; set; }
            public string? server { get; set; }
        }



        [HttpPost("login", Name = "Login")]
        public IActionResult Login([FromBody] LoginFormat data)
        {
            int s = service.ApiLogin(data.id, data.pass);
            switch (s)
            {
                case 0: return NotFound();
                case 1: return Ok();
            }
            return BadRequest();
        }

        [HttpPost("register", Name = "Register")]
        public IActionResult Register([FromBody] LoginFormat data)
        {
            bool s = service.ApiAddUser(data.id, data.pass);
            if (s) { return StatusCode(200); };
            return BadRequest();
        }


        [HttpGet("contacts", Name = "GetContacts")]
        public IActionResult GetContacts(string user)
        {
            // return all contacts of the connected user in JSON
            List<Json_Contact> contacts = service.ApiGetContacts(user);

            if (contacts != null)
            {
                return Ok(contacts);
            }
            // find a way to send error
            return NotFound();
        }

        [HttpGet("contacts/{id}", Name = "GetContact")]
        public IActionResult GetContact(string user, string id)
        {
            Json_Contact contact = service.ApiGetContact(user, id);

            if (contact == null)
            {
                return NotFound();
            }
            return Ok(contact);
        }

        [HttpPost("contacts", Name = "PostContact")]
        public IActionResult AddContact(string user,
                  [FromBody] Json_Contact contact)
        {
            bool s = service.ApiAddContact(user, contact);
            if (s) { return StatusCode(201); };
            return BadRequest();
        }

        [HttpPut("contacts/{id}", Name = "PutContact")]
        public IActionResult PutContact(string user, string id,
                  [FromBody] Json_Contact contact)
        {
            contact.Id = id;
            bool s = service.ApiEditContact(user, id, contact);
            if (s) { return StatusCode(204); };
            return NotFound();
        }

        [HttpDelete("contacts/{id}", Name = "DeleteContact")]
        public IActionResult DeleteContact(string user, string id)
        {
            bool s = service.ApiDeleteContact(user, id);
            if (s) { return StatusCode(204); };
            return NotFound();
        }

        [HttpPost("invitations", Name = "Invitations")]
        public IActionResult Invitations([FromBody] ApiFormat data)
        {
            Json_Contact contact = new Json_Contact();
            contact.Server = data.server;
            contact.Id = data.from;
            contact.Name = data.from;
            bool s = service.ApiAddContact(data.to, contact);
            if (s)
            {
                hub.Clients.Group(data.to).SendAsync("ReceiveMessage");
                return StatusCode(201);
            };
            return BadRequest();
        }

        [HttpPost("transfer", Name = "Transfer")]
        public IActionResult Transfer([FromBody] ApiFormat data)
        {
            bool s = service.ApiAddMessage(data.to, data.from, data.content, false);
            if (s)
            {
                if (hub.Clients.User(data.to) != null)
                {
                    hub.Clients.Group(data.to).SendAsync("ReceiveMessage");
                }
                return StatusCode(201);
            };
            return BadRequest();
        }

        [HttpGet("contacts/{id}/messages", Name = "GetMessages")]
        public IActionResult GetMessages(string user, string id)
        {
            // return all Messages of the connected user in JSON
            List<Json_Message> ret = service.ApiGetMessages(user, id);

            if (ret == null)
            {
                return NotFound();
            }
            // find a way to send error
            return Ok(ret);
        }

        [HttpGet("contacts/{contact}/messages/{id}", Name = "GetMessage")]
        public IActionResult GetMessage(string user, string contact, int id)
        {
            Json_Message ret = service.ApiGetMessage(user, contact, id);

            if (ret == null)
            {
                return NotFound();
            }
            // return the specific contact of the connected user in JSON
            return Ok(ret);
        }

        [HttpDelete("contacts/{contact}/messages/{id}", Name = "DeleteMessage")]
        public IActionResult DeleteMessage(string user, string contact, int id)
        {
            bool s = service.ApiDeleteMessage(user, contact, id);
            if (s) { return StatusCode(204); };
            return NotFound();
        }

        [HttpPost("contacts/{id}/messages", Name = "PostMessage")]
        public IActionResult PostMessage(string user, string id,
                  [FromBody] ApiFormat content)
        {
            bool s = service.ApiAddMessage(user, id, content.content, true);
            if (s) { return StatusCode(201); };
            return BadRequest();
        }

        [HttpPut("contacts/{contact}/messages/{id}", Name = "PutMessage")]
        public IActionResult PostMessage(string user, string contact, int id,
                  [FromBody] ApiFormat content)
        {
            bool s = service.ApiEditMessage(user, contact, content.content, id);
            if (s) { return StatusCode(204); };
            return BadRequest();
        }
    }
}
