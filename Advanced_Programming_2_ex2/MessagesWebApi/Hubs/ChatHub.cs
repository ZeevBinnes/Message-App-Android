using MessagesApp.Models;
using Microsoft.AspNetCore.SignalR;

namespace MessagesWebApi.Hubs

{
    public class ChatHub : Hub
    {
        private readonly IDictionary<string, string> _connections;

        public ChatHub(IDictionary<string, string> conn)
        {
            _connections = conn;
        }

        public override Task OnDisconnectedAsync(Exception exception)
        {
            if (_connections.TryGetValue(Context.ConnectionId, out string user))
            {
                _connections.Remove(Context.ConnectionId);
            }

            return base.OnDisconnectedAsync(exception);
        }

        public async Task Login(string user)
        {
            _connections[Context.ConnectionId] = user;
            await Groups.AddToGroupAsync(Context.ConnectionId, user);
        }
    }
}
