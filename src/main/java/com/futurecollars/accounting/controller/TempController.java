package com.futurecollars.accounting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/temp")
public class TempController {

  @GetMapping("/A")
  public String methodA() {
    return "methodA";
  }

  @GetMapping("/B")
  public String methodB() {
    return "methodB";
  }

  @PostMapping("/C")
  public String methodC() {
    return "methodC";
  }
}
