package com.eronalves.greeter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GreeterController.class)
public class GreetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private Clock clock;

  @Test
  public void testGreetMorning ()
      throws Exception {
    Clock fixedClock = Clock.fixed(
        LocalDateTime.of(2023, 12, 8, 11, 25, 00).toInstant(ZoneOffset.UTC),
        ZoneId.systemDefault()
    );

    when(clock.instant()).thenReturn(fixedClock.instant());
    when(clock.getZone()).thenReturn(fixedClock.getZone());

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("greet"))
        .andExpect(content().string(containsString("Good Morning")));
  }

}
