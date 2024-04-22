package io.github.almeida.renato.todolistapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: REMOVER ISSO
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test(){
        return "Passei na autenticação";
    }
}
