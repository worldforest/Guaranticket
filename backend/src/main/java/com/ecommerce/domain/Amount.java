package com.ecommerce.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Amount {
	private Integer total, tax_free, vat, point, discount;	
}
