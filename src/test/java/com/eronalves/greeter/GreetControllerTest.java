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
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(GreeterController.class)
public class GreetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private Clock clock;

  private Clock prepareClock (LocalDateTime time) {
    return Clock
        .fixed(time.toInstant(ZoneOffset.ofHours(-3)), ZoneId.systemDefault());
  }

  private void setupMocksForClock(Clock mockClock) {
    when(clock.instant()).thenReturn(mockClock.instant());
    when(clock.getZone()).thenReturn(mockClock.getZone());
  }

  private ResultActions doDefaultAssertions ()
      throws Exception {
    return mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("greet"));
  }

  @Test
  public void testGreetMorning ()
      throws Exception {
    Clock fixedClock = prepareClock(LocalDateTime.of(2023, 12, 8, 11, 25, 00));

    setupMocksForClock(fixedClock);

    doDefaultAssertions()
        .andExpect(content().string(containsString("Good Morning")));
  }

  @Test
  public void testGreetAfternoon ()
      throws Exception {
    Clock fixedClock = prepareClock(LocalDateTime.of(2023, 12, 8, 12, 42, 00));

    setupMocksForClock(fixedClock);

    doDefaultAssertions()
        .andExpect(content().string(containsString("Good Afternoon")));
  }

  @Test
  public void testGreetNight ()
      throws Exception {
    var fixedClock = prepareClock(LocalDateTime.of(2023, 12, 8, 18, 05));

    setupMocksForClock(fixedClock);

    doDefaultAssertions()
        .andExpect(content().string(containsString("Good Night")));
  }

}
