package com.abc.pojo.vo;

import com.abc.pojo.Orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo extends Orders{

    private String buyerName;

    private String sellerName;
}