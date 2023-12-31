package com.eronalves.greeter;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreeterController {

  private Clock clock;

  public GreeterController (Clock clock) {
    super();
    this.clock = clock;
  }

  @GetMapping("/")
  public String doGreet (Model model) {
    var nowHour = LocalDateTime.now(clock).getHour();

    if (nowHour < 12) model.addAttribute("greet", "Good Morning!!");
    else if (nowHour < 18) model.addAttribute("greet", "Good Afternoon!!");
    else model.addAttribute("greet", "Good Night!!");

    return "greet";
  }

}
