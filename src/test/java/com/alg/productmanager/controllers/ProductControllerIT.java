package com.alg.productmanager.controllers;

import com.alg.productmanager.configuration.BaseIntegrationTest;
import com.alg.productmanager.converters.ProductConverter;
import com.alg.productmanager.converters.AccountConverter;
import com.alg.productmanager.objects.dtos.AccountDto;
import com.alg.productmanager.objects.entities.Account;
import com.alg.productmanager.repository.ProductRepository;
import com.alg.productmanager.repository.AccountRepository;
import com.alg.productmanager.utils.ProductDtoFactory;
import com.alg.productmanager.utils.AccountDtoFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerIT extends BaseIntegrationTest {

  private MockMvc mockMvc;
  @Autowired private WebApplicationContext webApplicationContext;
  @Autowired private AccountRepository accountRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private AccountConverter accountConverter;
  @Autowired private ProductConverter productConverter;
  private Account simpleAccount, adminAccount;
  private AccountDto accountDto, adminDto;

  @BeforeAll
  public void init() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity())
            .build();

    accountDto = AccountDtoFactory.getSimpleAccountrDto();
    simpleAccount = accountConverter.toAccount(accountDto);
    simpleAccount = accountRepository.save(simpleAccount);

    adminDto = AccountDtoFactory.getAdminUserDto();
    adminAccount = accountConverter.toAccount(adminDto);
    adminAccount = accountRepository.save(adminAccount);
  }

  @BeforeEach
  public void setUp() {
  }

  @AfterEach
  public void tearDown() {
    // clear product repository
    productRepository.deleteAll();
    productRepository.flush();
  }

  @Test
  @SneakyThrows
  public void authenticatedUserCanCreateProduct() {

    var productDto = ProductDtoFactory.getProductDto();

    mockMvc
            .perform(
                    post("/api/v1/product/add")
                            .with(httpBasic(accountDto.getUsername(), accountDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    String.valueOf(objectMapper.writeValueAsString(productDto)))
            )
            .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void authenticatedAdminCannotCreateProduct() {

    var productDto = ProductDtoFactory.getProductDto();

    mockMvc
            .perform(
                    post("/api/v1/product/add")
                            .with(httpBasic(adminDto.getUsername(), adminDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    String.valueOf(objectMapper.writeValueAsString(productDto)))
            )
            .andExpect(status().isForbidden());
  }

  @Test
  @SneakyThrows
  public void userCanViewProduct() {

    var productDto = ProductDtoFactory.getProductDto();
    var product = productConverter.toEntity(productDto);
    product = productRepository.save(product);

    var response = mockMvc
            .perform(
                    get("/api/v1/product/get/" + product.getId())
                            .with(httpBasic(accountDto.getUsername(), accountDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    Assertions.assertTrue(response.getResponse().getContentAsString().contains("\"id\":" + product.getId()));
  }

  @Test
  @SneakyThrows
  public void adminCannotViewProduct() {

    var productDto = ProductDtoFactory.getProductDto();
    var product = productConverter.toEntity(productDto);
    product = productRepository.save(product);

    mockMvc
            .perform(
                    get("/api/v1/product/get/" + product.getId())
                            .with(httpBasic(adminDto.getUsername(), adminDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
  }

  @Test
  @SneakyThrows
  public void userCannotEditProduct() {

    var productDto = ProductDtoFactory.getProductDto();

    var product = productConverter.toEntity(productDto);
    productRepository.save(product);

    productDto = ProductDtoFactory.getAlteredProductDto();

    mockMvc
            .perform(
                    put("/api/v1/product/edit/" + product.getId())
                            .with(httpBasic(accountDto.getUsername(), accountDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    String.valueOf(objectMapper.writeValueAsString(productDto))))
            .andExpect(status().isForbidden());
  }

  @Test
  @SneakyThrows
  public void adminCanEditProduct() {

    var productDto = ProductDtoFactory.getProductDto();

    var product = productConverter.toEntity(productDto);
    productRepository.save(product);

    productDto = ProductDtoFactory.getAlteredProductDto();

    mockMvc
            .perform(
                    put("/api/v1/product/edit/" + product.getId())
                            .with(httpBasic(adminDto.getUsername(), adminDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    String.valueOf(objectMapper.writeValueAsString(productDto))))
            .andExpect(status().isOk());
  }

  @Test
  @SneakyThrows
  public void userCannotDeleteProduct() {

    var productDto = ProductDtoFactory.getProductDto();

    var productEntity = productConverter.toEntity(productDto);
    var newProduct = productRepository.save(productEntity);

    var productList = productRepository.findAll();
    Assertions.assertEquals(productList.size(), 1);

    mockMvc
            .perform(
                    delete("/api/v1/product/delete/" + newProduct.getId())
                            .with(httpBasic(accountDto.getUsername(), accountDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());

    productList = productRepository.findAll();
    Assertions.assertEquals(productList.size(), 1);
  }

  @Test
  @SneakyThrows
  public void adminCanDeleteProduct() {

    var productDto = ProductDtoFactory.getProductDto();

    var productEntity = productConverter.toEntity(productDto);
    var newProduct = productRepository.save(productEntity);

    var productList = productRepository.findAll();
    Assertions.assertEquals(productList.size(), 1);

    mockMvc
            .perform(
                    delete("/api/v1/product/delete/" + newProduct.getId())
                            .with(httpBasic(adminDto.getUsername(), adminDto.getPassword()))
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    productList = productRepository.findAll();
    Assertions.assertEquals(productList.size(), 0);
  }
}