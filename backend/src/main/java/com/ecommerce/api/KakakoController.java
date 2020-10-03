package com.ecommerce.api;

import com.ecommerce.application.IDealService;
import com.ecommerce.application.IJwtService;
import com.ecommerce.application.IKakakoService;
import com.ecommerce.application.ITicketService;
import com.ecommerce.application.impl.JwtService;
import com.ecommerce.domain.Deal;
import com.ecommerce.domain.DealDetail;
import com.ecommerce.domain.DealList;
import com.ecommerce.domain.Ticket;
import com.ecommerce.domain.TicketDetail;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class KakakoController
{
	public static final Logger logger = LoggerFactory.getLogger(KakakoController.class);

	@Autowired
	private IKakakoService kakakoService;
	@Autowired
	private IJwtService jwtService;
	
	@ApiOperation(value = "거래 진행")
	@RequestMapping(value ="/kakaoPay", method = RequestMethod.POST)
    public String kakaoPay(@RequestBody Ticket ticket) {
//		String token = request.getHeader("jwt-auth-token");
//		Map<String,Object>userinfo = jwtService.get(token);
//		long uid = (Long.parseLong(userinfo.get("USER").toString()));
//		ticket.setUid(uid);
        return kakakoService.kakaoPayReady(ticket);
    }
	
    @GetMapping("/kakaoPaySuccess")
    public ModelAndView kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
    	logger.info("kakaoPaySuccess get............................................");
        logger.info("kakaoPaySuccess pg_token : " + pg_token);
        model.addAttribute("info", kakakoService.kakaoPayInfo(pg_token));
        ModelAndView mav = new ModelAndView("redirect:http://localhost:8081");
        return mav;
    }
}
