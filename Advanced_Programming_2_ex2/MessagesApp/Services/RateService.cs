using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using MessagesApp.Data;
using MessagesApp.Models;

namespace MessagesApp.Services
{
    public class RateService
    {
        private readonly MessagesAppContext _context;
        private static List<Rate> _rates = new List<Rate>();

        public RateService(MessagesAppContext context)
        {
            _context = context;

            if (_rates.Count == 0)
            {
                Rate r = new Rate();
                r.NumRate = 5;
                r.Feedback = "excelent!";
                r.Time = DateTime.Now;
                r.RaterName = "Erel Akiva";
                r.Id = 1;
                _rates.Add(r);

                r = new Rate();
                r.NumRate = 4;
                r.Feedback = "very good!";
                r.Time = DateTime.Now;
                r.RaterName = "Chemi";
                r.Id = 2;
                _rates.Add(r);
            }
        }

        [HttpGet]
        public List<Rate> GetAll()
        {
            return _rates;
        }

        public Rate Get(int id)
        {
            return _rates.Find(x => x.Id == id);
        }

        public void Create(int numRate, string feedback, string raterName)
        {
            Rate rate = new Rate();
            if (_rates.Count == 0)
            {
                rate.Id = 1;
            }
            else
            {
                rate.Id = _rates.Max(x => x.Id) + 1;
            }
            rate.NumRate = numRate;
            rate.Feedback = feedback;
            rate.RaterName = raterName;
            rate.Time = DateTime.Now;
            _rates.Add(rate);
        }

        public void Create(Rate rate)
        {
            _rates.Add(rate);
        }

        public void Update(int id, int numRate, string feedback, string raterName)
        {
            Rate rate = Get(id);
            rate.NumRate = numRate;
            rate.Feedback = feedback;
            rate.RaterName = raterName;
        }

        public void Delete(int id)
        {
            _rates.Remove(Get(id));
        }
    }
}
