using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using Microsoft.Extensions.DependencyInjection;
using MessagesApp.Data;
using MessagesApp.Hubs;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

var MyAllowSpecificOrigins = "_myAllowSpecificOrigins";

builder.Services.AddCors(options =>
{
    options.AddPolicy(name: MyAllowSpecificOrigins,
                      policy =>
                      {
                          policy.AllowAnyHeader().AllowAnyMethod().SetIsOriginAllowed(x => true).AllowCredentials();
                      });
});

builder.Services.AddDbContext<MessagesAppContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("MessagesAppContext") ?? throw new InvalidOperationException("Connection string 'MessagesAppContext' not found.")));

// Add services to the container.
builder.Services.AddControllersWithViews();
builder.Services.AddControllers();
builder.Services.AddSignalR();
builder.Services.AddSingleton<IDictionary<string, string>>(opts => new Dictionary<string, string>());
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseCors(MyAllowSpecificOrigins);

app.UseRouting();

app.UseAuthorization();
//app.UseAuthentication();

app.MapControllers();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.UseEndpoints(endpoints =>
{
    endpoints.MapHub<ChatHub>("/login");
});

app.Run();