using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using MessagesApp.Data;
using MessagesApp.Hubs;

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

// Add services to the container.

builder.Services.AddControllers();

builder.Services.AddSignalR();
builder.Services.AddSingleton<IDictionary<string, string>>(opts => new Dictionary<string, string>());



var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseCors(MyAllowSpecificOrigins);

app.UseRouting();

app.UseAuthorization();

app.MapControllers();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.UseEndpoints(endpoints =>
{
    endpoints.MapHub<ChatHub>("/login");
});

app.Run();