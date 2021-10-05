package com.addressbook.rest;

import com.addressbook.AddressBookApplicationTest;
import com.addressbook.model.Contact;
import com.addressbook.util.Constants;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(classes = AddressBookApplicationTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UptimeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    public void test_fetchAndRemoveContactFromAddressBook() {
        EntityExchangeResult<Boolean> result = this.webTestClient.get()
                .uri("/uptime")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Boolean.class)
                .returnResult();
        Assertions.assertTrue(result.getResponseBody().booleanValue());
    }
}
