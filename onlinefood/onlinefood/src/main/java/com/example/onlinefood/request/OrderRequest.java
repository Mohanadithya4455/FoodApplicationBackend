package com.example.onlinefood.request;

import com.example.onlinefood.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
private Long restaurantId;
private Address deliveryAddress;
}
