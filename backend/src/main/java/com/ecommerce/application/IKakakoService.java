package com.ecommerce.application;

import com.ecommerce.domain.KakaoPayApproval;
import com.ecommerce.domain.Ticket;

public interface IKakakoService {
	
	String kakaoPayReady(Ticket ticket);
	
	KakaoPayApproval kakaoPayInfo(String pg_token, Ticket ticket);
}