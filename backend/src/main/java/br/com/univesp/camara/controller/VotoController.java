package br.com.univesp.camara.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deputados/{id}/votos")
@CrossOrigin(origins = "http://localhost:5173")

public class VotoController {
}
