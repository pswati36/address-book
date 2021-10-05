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
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest(classes = AddressBookApplicationTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookRestControllerTest {

    private static final String TEST_ADDRESS_BOOK = "testAddressBook";
    private static final String TEST_ADDRESS_BOOK_NA = "testAddressBookNA";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    public void test_addContactToAddressBook(){
        Contact contact = new Contact("Jack Sparrow", Arrays.asList("9090909090"));
        this.webTestClient.post()
                .uri(Constants.addressBookMapping+"/addressBookId/"+TEST_ADDRESS_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(contact))
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.displayName").isEqualTo("Jack Sparrow");
    }

    @Test
    @Order(2)
    public void test_fetchAndRemoveContactFromAddressBook(){
        EntityExchangeResult<List<Contact>> result = this.webTestClient.get()
                .uri(Constants.addressBookMapping+"/addressBookId/"+Constants.DEFAULT_ADDRESS_BOOK+"/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Contact.class)
                .returnResult();
        Contact fetchedContact = result.getResponseBody().get(0);
        Assertions.assertNotNull(fetchedContact.getId());
        Assertions.assertEquals("Will Smith",fetchedContact.getDisplayName());

        EntityExchangeResult<Contact> removedResult = this.webTestClient.delete()
                .uri(Constants.addressBookMapping+"/addressBookId/"+
                        Constants.DEFAULT_ADDRESS_BOOK+"/contactId/"+fetchedContact.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Contact.class)
                .returnResult();

        Assertions.assertNotNull(removedResult.getResponseBody().getId());
        Assertions.assertEquals(fetchedContact.getId(),removedResult.getResponseBody().getId());
    }

    @Test
    @Order(3)
    public void test_fetch_all_contact_and_unique_contacts(){
        EntityExchangeResult<List<Contact>> result = this.webTestClient.get()
                .uri(Constants.addressBookMapping+"/addressBookId/"+TEST_ADDRESS_BOOK+"/contacts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Contact.class)
                .returnResult();
        Assertions.assertNotNull(result.getResponseBody());
        Assertions.assertEquals(1,result.getResponseBody().size());

        Contact contact = new Contact("Jack Sparrow", Arrays.asList("9090909090"));
        this.webTestClient.post()
                .uri(Constants.addressBookMapping+"/addressBookId/"+TEST_ADDRESS_BOOK)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(contact))
                .exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.displayName").isEqualTo("Jack Sparrow");

        // Unique results
        result = this.webTestClient.get()
                .uri(Constants.addressBookMapping+"/contacts?unique=true")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Contact.class)
                .returnResult();
        Assertions.assertNotNull(result.getResponseBody());
        Assertions.assertEquals(1,result.getResponseBody().size());

        // Non unique results
        result = this.webTestClient.get()
                .uri(Constants.addressBookMapping+"/contacts?unique=false")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(Contact.class)
                .returnResult();
        Assertions.assertNotNull(result.getResponseBody());
        Assertions.assertEquals(2,result.getResponseBody().size());

    }

}
