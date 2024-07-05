package com.example.onlinefood.service;

import com.example.onlinefood.model.Address;
import com.example.onlinefood.model.Restaurant;
import com.example.onlinefood.model.RestaurantDto;
import com.example.onlinefood.model.User;
import com.example.onlinefood.repository.AddressRepository;
import com.example.onlinefood.repository.RestaurantRepository;
import com.example.onlinefood.repository.UserRepository;
import com.example.onlinefood.request.CreateRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RestaurantServiceImpl implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Restaurant createReastaurant(CreateRest createRest, User user) {
        Address address=createRest.getAddress();
        addressRepository.save(address);
        Restaurant restaurant=new Restaurant();
       // if(createRest.getName()!=null)
         restaurant.setName(createRest.getName());
       // if(createRest.getAddress()!=null)
        restaurant.setAddress(address);
      //  if(createRest.getDescription()!=null)
        restaurant.setDescription(createRest.getDescription());
       // if(createRest.getContactInformation()!=null)
        restaurant.setContactInformation(createRest.getContactInformation());
       // if(createRest.getImages()!=null)
        restaurant.setImages(createRest.getImages());
      //  if(createRest.getCuisineType()!=null)
        restaurant.setCuisineType(createRest.getCuisineType());
       // if(createRest.getOpeningHours()!=null)
        restaurant.setOpeningHours(createRest.openingHours);
        restaurant.setOwner(user);
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants(User user) {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> restaurant=restaurantRepository.findById(id);
        if(restaurant.isPresent()){
            return restaurant.get();
        }
        else{
            throw new Exception("Restaurant not found with this Id");
        }
    }

    @Override
    public Restaurant findRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("Restaurant not Found");
        }
        return restaurant;
    }

    @Override
    public void deleteRestaurant(Long id,User user) throws Exception {
     Restaurant restaurant = findRestaurantById(id);
     restaurantRepository.delete(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, User user,CreateRest rest) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
         restaurant.setName(rest.getName());
         restaurant.setContactInformation(rest.getContactInformation());
         restaurant.setCuisineType(rest.getCuisineType());
         Address address=rest.getAddress();
         addressRepository.save(address);
         restaurant.setAddress(address);
         restaurant.setImages(rest.getImages());
         restaurant.setDescription(rest.getDescription());
         restaurant.setOpeningHours(rest.getOpeningHours());
         restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id, User user) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
        if(restaurant.isOpen()){
            restaurant.setOpen(false);
        }
        else{
            restaurant.setOpen(true);
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public RestaurantDto addFavourites(Long id, User user) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
        RestaurantDto dto=new RestaurantDto();
        dto.setTitle(restaurant.getName());
        dto.setImages(restaurant.getImages());
        dto.setId(restaurant.getId());
        dto.setDescription(restaurant.getDescription());
        if(user.getFavourites().contains(dto)){
            user.getFavourites().remove(dto);
        }
        else{
            user.getFavourites().add(dto);
        }
        userRepository.save(user);
        return dto;

    }

    @Override
    public List<Restaurant> searchRestaurants(String str) {
        return restaurantRepository.findBySearchQuery(str);
    }
}
