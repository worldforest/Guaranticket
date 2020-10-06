package com.ecommerce.application.impl;

import com.ecommerce.application.IKakakoService;
import com.ecommerce.application.IPerformanceService;
import com.ecommerce.application.ITicketService;
import com.ecommerce.domain.KakaoPayApproval;
import com.ecommerce.domain.KakaoPayReady;
import com.ecommerce.domain.Performance;
import com.ecommerce.domain.Ticket;
import com.ecommerce.mapper.PerformanceMapper;
import com.ecommerce.mapper.TicketMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class KakakoService implements IKakakoService
{
	public static final Logger logger = LoggerFactory.getLogger(KakakoService.class);

	private static final String HOST = "https://kapi.kakao.com";
	
	private KakaoPayReady kakaoPayReady;
	
    private KakaoPayApproval kakaoPayApproval;	  
    
    private Ticket ticket;
    
    @Autowired
    private PerformanceMapper performanceMapper;
    
    @Autowired
    private TicketMapper ticketmapper;
    
	@Override
	public String kakaoPayReady(Ticket tickets) {
		RestTemplate restTemplate = new RestTemplate();
		ticket = tickets;
		Performance performance = performanceMapper.get(ticket.getPid());
		System.out.println(performance.toString());
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "c400fcd2965f6b8ab5a7161d409e43de");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "GUARANTICKET");
        params.add("partner_user_id", "user");
        params.add("item_name", performance.getTitle()+"("+ticket.getDate()+" "+ticket.getTime()+" "+ticket.getGrade()+" "+ticket.getSeatNumber()+"석)");
        params.add("quantity", "1");
        params.add("total_amount", ticket.getPrice());
        params.add("tax_free_amount", "100");
        params.add("approval_url", "https://j3b101.p.ssafy.io/api/kakaoPaySuccess");
        params.add("cancel_url", "https://j3b101.p.ssafy.io");
        params.add("fail_url", "https://j3b101.p.ssafy.io");
//        params.add("approval_url", "http://localhost:8080/api/kakaoPaySuccess");
//        params.add("cancel_url", "http://localhost:8081");
//        params.add("fail_url", "http://localhost:8081");
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
        	kakaoPayReady = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReady.class);
            logger.info("" + kakaoPayReady);
            return kakaoPayReady.getNext_redirect_pc_url();
 
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "/pay";
	}
	
	public KakaoPayApproval kakaoPayInfo(String pg_token) {
		 
		logger.info("KakaoPayInfo............................................");
		logger.info("-----------------------------");
        
        RestTemplate restTemplate = new RestTemplate();
 
        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "c400fcd2965f6b8ab5a7161d409e43de");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
 
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReady.getTid());
        params.add("partner_order_id", "GUARANTICKET");
        params.add("partner_user_id", "user");
        params.add("pg_token", pg_token);
        params.add("total_amount", ticket.getPrice());
        
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        
        try {
            kakaoPayApproval = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApproval.class);
            logger.info("" + kakaoPayApproval);
            ticketmapper.create(ticket);
            return kakaoPayApproval;
        
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

}
