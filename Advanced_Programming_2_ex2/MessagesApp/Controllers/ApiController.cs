using Microsoft.AspNetCore.Mvc;
using MessagesApp.Services;
using Microsoft.AspNetCore.SignalR;
using Microsoft.AspNetCore.Cors;
using MessagesApp.Hubs;
using MessagesApp.Models;
using Microsoft.EntityFrameworkCore;
using MessagesApp.Data;
using FirebaseAdmin.Messaging;

namespace MessagesApp.Controllers
{
    [Route("api")]
    [ApiController]
    public class ApiController : ControllerBase
    {

        private readonly IHubContext<ChatHub> hub;
        private readonly MessagesAppContext _context;



        public ApiController(IHubContext<ChatHub> hubContext, MessagesAppContext context)
        {
            this.hub = hubContext;
            _context = context;
        }

        public class LoginFormat
        {
            public string id { get; set; }
            public string pass { get; set; }

            public string token { get; set; }
        }
        public class ApiFormat
        {
            public string from { get; set; }
            public string to { get; set; }
            public string content { get; set; }
            public string server { get; set; }
        }

       



        [HttpPost("login", Name = "Login")]
        public async Task<IActionResult> Login([FromBody] LoginFormat data)
        {
            if (data.id == null || _context.Users == null)
            {
                return NotFound();
            }

            var user = await _context.Users.FindAsync(data.id);
            if (user == null)
            {
                return NotFound();
            }

            if (user.Password == data.pass)
            {
                user.Token = data.token;

                await _context.SaveChangesAsync();
                _context.Entry(user).State = EntityState.Modified;
                return Ok();
            }
            return BadRequest();
        }

        [HttpPost("register", Name = "Register")]
        public async Task<IActionResult> Register([FromBody] LoginFormat data)
        {
            if (data.id == null || _context.Users == null)
            {
                return NotFound();
            }

            var user = await _context.Users.FindAsync(data.id);
            if (user != null)
            {
                return NotFound();
            }
            user = new User();
            user.Userid = data.id;
            user.Password = data.pass;
            user.Token = data.token;
            _context.Add(user);
            await _context.SaveChangesAsync();
            return StatusCode(200);

        }


        [HttpGet("contacts", Name = "GetContacts")]
        public async Task<IActionResult> GetContacts(string user)
        {
            if (user == null || _context.Contacts == null)
            {
                return NotFound();
            }

            var _user = await _context.Users.FindAsync(user);
            if (_user == null)
            {
                return NotFound();
            }
            var contacts = await _context.Contacts.Where(c => c.Userid == user).ToListAsync();
            List<JsonContact> ret = new List<JsonContact>();
            foreach (Contact c in contacts)
            {
                ret.Add(JsonContact.GetJsonContact(c));
            }

            return Ok(ret);

        }

        [HttpGet("contacts/{id}", Name = "GetContact")]
        public async Task<IActionResult> GetContact(string user, string id)
        {
            if (user == null || _context.Users == null || _context.Contacts == null)
            {
                return NotFound();
            }

            var _user = await _context.Users.FindAsync(user);
            if (_user == null)
            {
                return NotFound();
            }
            var contact = await _context.Contacts.Where(c => c.Userid == user).SingleAsync(c => c.Contactid == id);
            if (contact == null) { return NotFound(); }
            JsonContact jcontact = JsonContact.GetJsonContact(contact);
            return Ok(jcontact);
        }

        [HttpPost("contacts", Name = "PostContact")]
        public async Task<IActionResult> AddContact(string user,
                  [FromBody] JsonContact contact)
        {
            if (user == null || contact == null || _context.Contacts == null)
            {
                return BadRequest();
            }
            var _user = await _context.Users.FindAsync(user);
            if (_user == null) { return BadRequest(); }
            var exist = await _context.Contacts.AnyAsync(c => c.Userid == user && c.Contactid == contact.Id);
            if (exist) { return BadRequest(); }
            var _contact = new Contact();
            _contact.Userid = user;
            _contact.Contactid = contact.Id;
            _contact.Nickname = contact.Name;
            _contact.Server = contact.Server;
            _contact.LastContent = null;
            _contact.LastDate = DateTime.Now;
            _context.Contacts.Add(_contact);
            await _context.SaveChangesAsync();
            return StatusCode(201);
        }

        [HttpPut("contacts/{id}", Name = "PutContact")]
        public async Task<IActionResult> PutContact(string user, string id,
                  [FromBody] JsonContact contact)
        {
            if (id == null || contact == null || _context.Contacts == null)
            {
                return NotFound();
            }
            if (contact == null) { return NotFound(); }
            var _user = await _context.Users.FindAsync(user);
            if (_user == null) { return NotFound(); }
            var _contact = await _context.Contacts.Where(c => c.Userid == user).SingleAsync(c => c.Contactid == contact.Id);
            if (_contact == null) { return NotFound(); }
            _contact.Nickname = contact.Name;
            _contact.Server = contact.Server;
            _context.Entry(_contact).State = EntityState.Modified;
            await _context.SaveChangesAsync();
            return StatusCode(204);
        }

        [HttpDelete("contacts/{id}", Name = "DeleteContact")]
        public async Task<IActionResult> DeleteContact(string user, string id)
        {
            var _user = await _context.Users.FindAsync(user);
            if (_user == null) { return NotFound(); }
            var contact = await _context.Contacts.Where(c => c.Userid == user).SingleAsync(c => c.Contactid == id);
            if (contact == null) { return NotFound(); }
            _context.Contacts.Remove(contact);
            await _context.SaveChangesAsync();
            return StatusCode(204);
        }

        [HttpPost("invitations", Name = "Invitations")]
        public async Task<IActionResult> Invitations([FromBody] ApiFormat data)
        {
            if (data == null|| _context.Contacts == null)
            {
                return NotFound();
            }
            if (data == null) { return BadRequest(); }
            var user = await _context.Users.FindAsync(data.to);
            if (user == null) { return BadRequest(); }
            var exist = await _context.Contacts.AnyAsync(c => c.Userid == data.to && c.Contactid == data.from);
            if (exist) { return BadRequest(); }
            var contact = new Contact();
            contact.Userid = data.to;
            contact.Contactid = data.from;
            contact.Nickname = data.from;
            contact.Server = data.server;
            contact.LastContent = null;
            contact.LastDate = DateTime.Now;
            _context.Contacts.Add(contact);
            await _context.SaveChangesAsync();
            await hub.Clients.Group(data.to).SendAsync("ReceiveMessage");
            return StatusCode(201);
        }

        [HttpPost("transfer", Name = "Transfer")]
        public async Task<IActionResult> Transfer([FromBody] ApiFormat data)
        {
            if (data.content == null) { return BadRequest(); }
            var user = await _context.Users.FindAsync(data.to);
            if (user == null) { return BadRequest(); }
            var contact = await _context.Contacts.Where(c => c.Userid == data.to).SingleAsync(c => c.Contactid == data.from);
            if (contact == null) { return BadRequest(); }
            Models.Message message = new Models.Message();
            message.Userid = data.to;
            message.Contactid = data.from;
            message.Content = data.content;
            message.Sent = false;
            message.Time = DateTime.Now;
            _context.Messages.Add(message);
            await _context.SaveChangesAsync();
            var fire = new FirebaseAdmin.Messaging.Message()
            {
                Notification =
                {
                    Body = message.Content,
                    Title = message.Contactid
                },
                Token = user.Token,
            };

            // Send a message to the device corresponding to the provided
            // registration token.
            string response = await FirebaseMessaging.DefaultInstance.SendAsync(fire);

            if (hub.Clients.User(data.to) != null)
            {
                await hub.Clients.Group(data.to).SendAsync("ReceiveMessage");
            }
            return StatusCode(201);
        }

        [HttpGet("contacts/{id}/messages", Name = "GetMessages")]
        public async Task<IActionResult> GetMessages(string user, string id)
        {
            var _user = await _context.Users.FindAsync(user);
            if (_user == null) { return NotFound(); }
            var contact = await _context.Contacts.Where(c => c.Userid == user).SingleAsync(c => c.Contactid == id);
            if (contact == null) { return NotFound(); }
            var messages = await _context.Messages.Where(m => m.Userid == user && m.Contactid == id).ToListAsync();
            List<JsonMessage> ret = new List<JsonMessage>();
            foreach (Models.Message message in messages)
            {
                ret.Add(JsonMessage.GetJsonMessage(message));
            }
            return Ok(ret);
        }

        [HttpGet("contacts/{contact}/messages/{id}", Name = "GetMessage")]
        public async Task<IActionResult> GetMessage(string user, string contact, int id)
        {
            var message = await _context.Messages.Where(m => m.Userid == user && m.Contactid == contact).SingleAsync(c =>c.Id == id);
            if (message == null) { return NotFound(); }
            return Ok(JsonMessage.GetJsonMessage(message));

        }

        [HttpDelete("contacts/{contact}/messages/{id}", Name = "DeleteMessage")]
        public async Task<IActionResult> DeleteMessage(string user, string contact, int id)
        {
            var message = await _context.Messages.Where(m => m.Userid == user && m.Contactid == contact).SingleAsync(c =>c.Id == id);
            if (message == null) { return NotFound(); }
            _context.Messages.Remove(message);
            
            await _context.SaveChangesAsync();
            return StatusCode(204);
        }

        [HttpPost("contacts/{id}/messages", Name = "PostMessage")]
        public async Task<IActionResult> PostMessage(string user, string id,
                  [FromBody] ApiFormat content)
        {
            if (content.content == null) { return BadRequest(); }
            var contact = await _context.Contacts.Where(c => c.Userid == user).SingleAsync(c => c.Contactid == id);
            if (contact == null) { return BadRequest(); }

            Models.Message message = new Models.Message();
            message.Userid = user;
            message.Contactid = id;
            message.Content = content.content;
            message.Sent = true;
            message.Time = DateTime.Now;

            _context.Messages.Add(message);

            contact.LastContent = message.Content;
            contact.LastDate = message.Time;
            _context.Entry(contact).State = EntityState.Modified;

            await _context.SaveChangesAsync();
            return StatusCode(201);

        }

        [HttpPut("contacts/{contact}/messages/{id}", Name = "PutMessage")]
        public async Task<IActionResult> PostMessage(string user, string contact, int id,
                  [FromBody] ApiFormat content)
        {
            var message = await _context.Messages.Where(m => m.Userid == user && m.Contactid == contact).SingleAsync(c => c.Id == id);
            if (message == null) { return NotFound(); }
            message.Content = content.content;
            _context.Entry(message).State = EntityState.Modified;
            await _context.SaveChangesAsync();
            return StatusCode(204);
        }
    }
}
