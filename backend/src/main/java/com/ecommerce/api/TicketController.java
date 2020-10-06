package com.ecommerce.api;

import com.ecommerce.application.IJwtService;
import com.ecommerce.application.IKakakoService;
import com.ecommerce.application.ITicketService;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.TicketList;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TicketController
{
   public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

   private ITicketService ticketService;
   private IJwtService jwtService;
   private Ticket tickets;
   
   @Autowired
   public TicketController(ITicketService ticketService,
         IJwtService jwtService) {
      Assert.notNull(ticketService, "dealService 개체가 반드시 필요!");
      this.ticketService = ticketService;
      this.jwtService = jwtService;
   }
   @Autowired
   private IKakakoService kakakoService;
   
   @ApiOperation(value = "공연 예매 하기")
   @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public String kakaoPay(@RequestBody Ticket ticket, HttpServletRequest request) {
		Ticket ticketValidation = null;
		// 결제전에 한번더 확인 좌석 결제 이미 된건지 확인
		ticketValidation = ticketService.getByPidAndDateAndTimeAndSeatNumber(ticket.getPid(), ticket.getDate(),
				ticket.getTime(), ticket.getSeatNumber());
		if(ticketValidation != null) {
			return "NOTEMPTY";
		}
		else {
			String token = request.getHeader("jwt-auth-token");
			Map<String,Object>userinfo = jwtService.get(token);
			long uid = (Long.parseLong(userinfo.get("USER").toString()));
			ticket.setUid(uid);
	        return kakakoService.kakaoPayReady(ticket);
		}
		
    }
   
    @GetMapping("/kakaoPaySuccess")
    public ModelAndView kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
       logger.info("kakaoPaySuccess get............................................");
       logger.info("kakaoPaySuccess pg_token : " + pg_token);
       model.addAttribute("ticket", tickets);
//        ModelAndView mav = new ModelAndView("redirect:http://localhost:8081/purchaselist");
       ModelAndView mav = new ModelAndView("redirect:https://j3b101.p.ssafy.io/purchaselist");
        return mav;
    }
   @ApiOperation(value = "공연(날짜+시간)별 예매 내역 검색")
   @RequestMapping(value = "/ticket/pid/{pid}/date/{date}/time/{time}", method = RequestMethod.GET)
   public List<Ticket> getByPidAndDateAndTime(@PathVariable long pid,
         @PathVariable String date,
         @PathVariable String time) {
      List<Ticket> list = ticketService.getByPidAndDateAndTime(pid,date,time);
      if (list == null || list.isEmpty()) {
         throw new NotFoundException(pid + "공연의 "+date+" 날짜의 예매 내역을 찾을 수 없습니다.");
      }
      return list;
   }
   @ApiOperation(value = "공연별 예매 내역 검색")
   @RequestMapping(value = "/ticket/pid/{pid}", method = RequestMethod.GET)
   public List<Ticket> getByPid(@PathVariable long pid) {
      
      List<Ticket> list = ticketService.getByPid(pid);

      if (list == null || list.isEmpty())
         throw new NotFoundException(pid + "공연의 예매 내역을 찾을 수 없습니다.");

      return list;
   }
   @ApiOperation(value = "사용자 예매 내역리스트 검색")
   @RequestMapping(value = "/ticket/uid", method = RequestMethod.GET)
   public List<TicketList> getByUid(HttpServletRequest request) {
      String token = request.getHeader("jwt-auth-token");
      Map<String,Object>userinfo = jwtService.get(token);
      long uid = (Long.parseLong(userinfo.get("USER").toString()));
      
      List<TicketList> list = ticketService.getByUid(uid);
      if (list == null || list.isEmpty())
         throw new NotFoundException(uid + "유저의 예매 내역을 찾을 수 없습니다.");
      return list;
   }
   
   @ApiOperation(value = "티켓 상세정보 검색")
   @RequestMapping(value = "/ticket/tid/{tid}", method = RequestMethod.GET)
   public TicketDetail getBytid(@PathVariable long tid) {
      
      TicketDetail ticketDetail = ticketService.get(tid);

      if (ticketDetail == null)
         throw new NotFoundException(tid + "의 티켓 정보를 찾을 수 없습니다.");

      return ticketDetail;
   }
}