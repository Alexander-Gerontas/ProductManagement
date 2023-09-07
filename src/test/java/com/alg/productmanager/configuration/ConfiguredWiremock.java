package com.alg.productmanager.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.SneakyThrows;
import org.springframework.boot.test.context.TestConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@TestConfiguration
public class ConfiguredWiremock {

  public final WireMockServer wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());

  public ConfiguredWiremock() {
    wireMockServer.start();
    configureFor("localhost", wireMockServer.port());
    setStubs();
  }

  /** Presets responses for certain requests to other applications. */
  @SneakyThrows
  void setStubs() {
    // Nothing here.
  }
}
