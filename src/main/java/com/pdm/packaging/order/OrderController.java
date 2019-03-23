package com.pdm.packaging.order;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @RequestMapping("/order")
    public Order order(@RequestParam(value="name", defaultValue="World") String name) {
        return new Order(1, 1, 1, false, 25);
    }
}
