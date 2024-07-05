package com.example.onlinefood.service;

import com.example.onlinefood.model.*;
import com.example.onlinefood.repository.AddressRepository;
import com.example.onlinefood.repository.OrderItemRepository;
import com.example.onlinefood.repository.OrderRepository;
import com.example.onlinefood.repository.UserRepository;
import com.example.onlinefood.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    CartService cartService;
    @Override
    public Order createOrder(OrderRequest req, User user) throws Exception {

        Address shippAddress= req.getDeliveryAddress();

        Address savedAddress=addressRepository.save(shippAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Order order=new Order();
        order.setCustomer(user);
        order.setCreatedAt(new Date());
        order.setOrderStatus("PENDING");
        order.setRestaurant(restaurant);
        Cart cart=cartService.findCartByUserId(user.getId());
        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem item:cart.getItem()){
            OrderItem orderItem=new OrderItem();
            orderItem.setFood(item.getFood());
            orderItem.setIngredients(item.getIngredients());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setTotalPrice(item.getTotalPrice());
            OrderItem savedItem=orderItemRepository.save(orderItem);
            orderItems.add(savedItem);
        }
         order.setItems(orderItems);
        Long totalPrice=cartService.calculateCartTotals(cart);
        order.setTotalPrice(totalPrice);
        Order savedOrder=orderRepository.save(order);
        restaurant.getOrders().add(savedOrder);
        return savedOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order=findById(orderId);
        if(orderStatus.equals("DELIVERED")||orderStatus.equals("PENDING")
        ||orderStatus.equals("COMPLETED")||orderStatus.equals("OUT_FOR_DELIVERY")){
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return order;
        }
         throw new Exception("Please Select Valid Status");

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
    Order order=findById(orderId);
    orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) {
        List<Order> orders=orderRepository.findByCustomerId(userId);
        return orders;
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) {
        List<Order> list=orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            list = list.stream().filter(order->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public Order findById(Long id) throws Exception {
        Optional<Order> order=orderRepository.findById(id);
        if(order.isEmpty()){
            throw new Exception("Order not found");
        }
        return order.get();
    }
}
