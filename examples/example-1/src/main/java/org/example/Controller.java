package org.example;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("*")
    String str() {
        return "Home";
    }
}
