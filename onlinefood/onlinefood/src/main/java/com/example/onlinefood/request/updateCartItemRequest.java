package com.example.onlinefood.request;

import lombok.Data;

@Data
public class updateCartItemRequest {

    Long cartItemId;
    int quantity;
}
