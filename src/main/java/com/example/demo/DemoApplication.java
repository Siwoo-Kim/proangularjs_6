package com.example.demo;

import lombok.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    ViewResolver viewResolver() {
        return new InternalResourceViewResolver("/static/", ".html");
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/index").setViewName("index");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @RestController
    public static class SportsStoreController {

        @NoArgsConstructor @ToString
        @AllArgsConstructor @Getter @Setter
        public static class Product {
            private Long id;
            private String name;
            private String description;
            private String category;
            private Double price;
        }

        @ToString
        @NoArgsConstructor
        @AllArgsConstructor @Getter @Setter
        public static class Order {
            private String name;
            private String street;
            private String city;
            private String state;
            private String zip;
            private String country;
            private Boolean giftwrap = false;
            private Long id;

            private static Long idTracker = 0l;
            private List<Product> products = new ArrayList<>();

            synchronized private static Long assignId() {
                return ++idTracker;
            }

        }



        public static List<Product> products = new ArrayList<>();
        public static List<Order> orders = new ArrayList<>();

        static  {
            products = new ArrayList<>();

            products.add(new Product(1l,"Kayak","1인용 보트", "수상스포츠", 275.));
            products.add(new Product(2l,"Lifejacket","멋진 보호 장비", "수상스포츠", 48.95));
            products.add(new Product(3l,"Soccer Ball","FIFA 인증 규격 및 무게", "축구", 15.95));
            products.add(new Product(4l,"Thinking Cap","두뇌 효율 개선", "축구", 19.5));
            products.add(new Product(5l,"Stadium","35,000좌석 경기장", "축구", 79500.));
        }

        @GetMapping("/products")
        public List<Product> products() {
            System.out.println("Get /products");
            return products;
        }

        @PostMapping("/products")
        public Product save(@RequestBody Product product) {
            System.out.println("Post /products");
            System.out.println(product);
            products.add(product);
            return product;
        }

        @DeleteMapping("/products/{id}")
        public void delete(@PathVariable Long id) {
            System.out.println("Delete /products");
            products.stream()
                    .filter(_product -> _product.id.equals(id))
                    .findFirst()
                    .ifPresent(_product -> products.remove(_product));
            System.out.println(products);
        }

        @PostMapping("/products/{id}")
        public Product update(@RequestBody Product product, @PathVariable Long id) {
            System.out.println("Update /products");

            products.stream()
                    .filter(_product -> _product.id.equals(product.id))
                    .findFirst()
                    .ifPresent(_product -> {
                        _product.setCategory(product.getCategory());
                        _product.setName(product.getName());
                        _product.setDescription(product.getDescription());
                        _product.setPrice(product.getPrice());
                    });
            return product;
        }

        @PostMapping("/orders")
        public Order order(@RequestBody Order order) {
            System.out.println(order);
            createOrder(order);
            System.out.println(orders);
            return order;
        }

        @GetMapping("/orders")
        public List<Order> getAll() {
            System.out.println("Triggered");
            System.out.println(orders);
            return orders;
        }

        private void createOrder(Order order) {
            order.setId(Order.assignId());
            orders.add(order);
        }

    }

    @RestController
    public static class AdminController {

        @NoArgsConstructor @AllArgsConstructor
        @Getter @Setter @ToString
        public static class User {
            private String id;
            private String username;
            private String password;


            public boolean login(User counterpart) {
                if(StringUtils.hasText(counterpart.username)
                        && StringUtils.hasText(counterpart.password)) {
                    return username.equals(counterpart.username) && password.equals(counterpart.password);
                }
                return false;
            }
        }

        private static User admin;

        static {
            admin = new User("admin@email.com", "admin", "1234");
        }

        @PostMapping("/users/login")
        public ResponseEntity<Object> Login(@RequestBody User user) {
            System.out.println(user);
            if(admin.login(user)) {
                System.out.println("Login success");
                return new ResponseEntity<Object>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
