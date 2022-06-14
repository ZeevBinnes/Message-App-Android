using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MessagesApp.Migrations
{
    public partial class Init : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Rate",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    NumRate = table.Column<int>(type: "int", nullable: false),
                    Feedback = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    RaterName = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Rate", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "User",
                columns: table => new
                {
                    Username = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Password = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Picture = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_User", x => x.Username);
                });

            migrationBuilder.CreateTable(
                name: "Chat",
                columns: table => new
                {
                    User = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Nickname = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Server = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Username = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Chat", x => x.User);
                    table.ForeignKey(
                        name: "FK_Chat_User_Username",
                        column: x => x.Username,
                        principalTable: "User",
                        principalColumn: "Username");
                });

            migrationBuilder.CreateTable(
                name: "Message",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    From = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    To = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Time = table.Column<DateTime>(type: "datetime2", nullable: false),
                    Content = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Type = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    ChatUser = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Message", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Message_Chat_ChatUser",
                        column: x => x.ChatUser,
                        principalTable: "Chat",
                        principalColumn: "User");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Chat_Username",
                table: "Chat",
                column: "Username");

            migrationBuilder.CreateIndex(
                name: "IX_Message_ChatUser",
                table: "Message",
                column: "ChatUser");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Message");

            migrationBuilder.DropTable(
                name: "Rate");

            migrationBuilder.DropTable(
                name: "Chat");

            migrationBuilder.DropTable(
                name: "User");
        }
    }
}
