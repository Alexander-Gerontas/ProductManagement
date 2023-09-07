package com.alg.productmanager.controllers;

import com.alg.productmanager.configuration.BaseIntegrationTest;
import com.alg.productmanager.converters.AccountConverter;
import com.alg.productmanager.converters.ProductConverter;
import com.alg.productmanager.repository.AccountRepository;
import com.alg.productmanager.repository.ProductRepository;
import com.alg.productmanager.utils.AccountDtoFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationControllerIT extends BaseIntegrationTest {

  private MockMvc mockMvc;
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private AccountRepository accountRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private AccountConverter accountConverter;
  @Autowired private ProductConverter productConverter;

  @BeforeAll
  public void init() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity())
            .build();
  }

  @BeforeEach
  public void setUp() {
  }

  @AfterEach
  public void tearDown() {
    // clear repos
    accountRepository.deleteAll();
    accountRepository.flush();
  }

  @Test
  @SneakyThrows
  public void accountRegistrationTest() {

    var account1 = AccountDtoFactory.getSimpleAccountrDto();

    mockMvc = webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(
                    post("/api/v1/account/registration")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    String.valueOf(objectMapper.writeValueAsString(account1))))
            .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void accountExistsTest() {

    var accountDto = AccountDtoFactory.getSimpleAccountrDto();
    var account = accountConverter.toAccount(accountDto);
    accountRepository.save(account);

    mockMvc = webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(
                    post("/api/v1/account/registration")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    String.valueOf(objectMapper.writeValueAsString(accountDto))))
            .andExpect(status().isBadRequest());
  }
}