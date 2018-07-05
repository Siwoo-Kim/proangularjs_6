package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    public static class SportsStoreController {

        @AllArgsConstructor @Getter @Setter
        public static class Product {
            private Long id;
            private String name;
            private String description;
            private String category;
            private Double price;
        }

        public static List<Product> products = new ArrayList<>();

        static  {
            products = Arrays.asList(
                    new Product(1l,"Kayak","1인용 보트", "수상스포츠", 275.),
                    new Product(2l,"Lifejacket","멋진 보호 장비", "수상스포츠", 48.95),
                    new Product(3l,"Soccer Ball","FIFA 인증 규격 및 무게", "축구", 15.95),
                    new Product(4l,"Thinking Cap","두뇌 효율 개선", "축구", 19.5),
                    new Product(5l,"Stadium","35,000좌석 경기장", "축구", 79500.)
            );
        }

        @GetMapping("/sports")
        public List<Product> products() {
            return products;
        }
    }
}
