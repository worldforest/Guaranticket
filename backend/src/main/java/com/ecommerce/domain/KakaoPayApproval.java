package com.ecommerce.domain;

import java.util.Date;

import lombok.Data;

@Data
public class KakaoPayApproval {
	
	private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private Amount amount;
    private Card card_info;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private Date created_at, approved_at;
	
}
