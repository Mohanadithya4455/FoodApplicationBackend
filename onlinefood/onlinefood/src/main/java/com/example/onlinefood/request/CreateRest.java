package com.example.onlinefood.request;

import com.example.onlinefood.model.Address;
import com.example.onlinefood.model.ContactInformation;
import com.example.onlinefood.model.Order;
import com.example.onlinefood.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRest {
    public Long id;
    public String name;

    public String description;

    public String cuisineType;

    public Address address;

    public ContactInformation contactInformation;

    public String openingHours;

    public List<String> images;

}
