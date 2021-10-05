package com.addressbook.service;


import com.addressbook.exception.ServiceException;
import com.addressbook.model.Contact;
import com.addressbook.util.Constants;
import org.junit.jupiter.api.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactServiceTest {


    private static final String TEST_ADDRESS_BOOK = "testAddressBook";
    private static final String TEST_ADDRESS_BOOK_NA = "testAddressBookNA";
    private static ContactService contactService;

    @BeforeAll
    static void initService(){
        contactService = new ContactService();
    }

    @Test
    @Order(1)
    void test_add_and_fetch_contact(){

        Contact contact = new Contact("Jack Sparrow", Arrays.asList("9090909090"));
        contact = contactService.addContactToAddressBook(TEST_ADDRESS_BOOK,contact);
        Assertions.assertNotNull(contact.getId());

        List<Contact> testAddressBookContacts = contactService.fetchAllContactByAddressBook(TEST_ADDRESS_BOOK);
        Assertions.assertEquals(1,testAddressBookContacts.size());

    }

    @Test
    @Order(2)
    void test_fetch_and_remove_contact(){
        // Fetch Contact by address book
        List<Contact> testAddressBookContacts = contactService.fetchAllContactByAddressBook(TEST_ADDRESS_BOOK);
        Assertions.assertEquals(1,testAddressBookContacts.size());

        String contactIdToRemove = testAddressBookContacts.get(0).getId();
        Contact removedContact = contactService.removeContactFromAddressBook(TEST_ADDRESS_BOOK,contactIdToRemove);
        Assertions.assertEquals(contactIdToRemove,removedContact.getId());

    }

    @Test
    @Order(3)
    void test_fetch_default_contacts(){

        // Fetch Contact by address book
        List<Contact> testAddressBookContacts = contactService.fetchAllContactByAddressBook(Constants.DEFAULT_ADDRESS_BOOK);
        Assertions.assertEquals(1,testAddressBookContacts.size());

    }

    @Test
    @Order(4)
    void test_fetch_and_remove_from_invalid_address_book(){
        Assertions.assertThrows(ServiceException.class,()->contactService.fetchAllContactByAddressBook(TEST_ADDRESS_BOOK_NA));

        Assertions.assertThrows(ServiceException.class,()->contactService.removeContactFromAddressBook(TEST_ADDRESS_BOOK_NA,"123"));
    }

    @Test
    @Order(5)
    void test_remove_invalid_contact_id(){
        Assertions.assertThrows(ServiceException.class,()->contactService.removeContactFromAddressBook(TEST_ADDRESS_BOOK,"123"));
    }

    @Test
    @Order(6)
    void test_fetch_all_contacts(){

        // Fetch Contact by address book
        List<Contact> testAddressBookContacts = contactService.fetchAllContacts();
        Assertions.assertEquals(1,testAddressBookContacts.size());

    }

    @Test
    @Order(7)
    void test_fetch_all_unique_contacts(){

        Contact contact = new Contact("Mythili", Arrays.asList("014996789"));
        contact = contactService.addContactToAddressBook(Constants.DEFAULT_ADDRESS_BOOK,contact);
        Assertions.assertNotNull(contact);

        contact = new Contact("Jack Sparrow", Arrays.asList("9090909090"));
        contact = contactService.addContactToAddressBook(Constants.DEFAULT_ADDRESS_BOOK,contact);
        Assertions.assertNotNull(contact);

        contact = new Contact("Jack Sparrow", Arrays.asList("9090909090"));
        contact = contactService.addContactToAddressBook(TEST_ADDRESS_BOOK,contact);
        Assertions.assertNotNull(contact);

        // Fetch Contact by address book
        List<Contact> defaultAddressBookUniqueContacts = contactService.fetchAllUniqueContacts();
        Assertions.assertEquals(3,defaultAddressBookUniqueContacts.size());

    }

    @Test
    @Order(8)
    void test_fetch_all_unique_contacts_by_address_book(){

        Contact contact = new Contact("Mythili", Arrays.asList("014996789"));
        contact = contactService.addContactToAddressBook(Constants.DEFAULT_ADDRESS_BOOK,contact);
        Assertions.assertNotNull(contact);

        contact = new Contact("Jack Sparrow", Arrays.asList("9090909090"));
        contact = contactService.addContactToAddressBook(Constants.DEFAULT_ADDRESS_BOOK,contact);
        Assertions.assertNotNull(contact);

        // Fetch Contact by address book
        List<Contact> defaultAddressBookUniqueContacts = contactService.fetchAllUniqueContactByAddressBook(Constants.DEFAULT_ADDRESS_BOOK);
        Assertions.assertEquals(3,defaultAddressBookUniqueContacts.size());

        defaultAddressBookUniqueContacts = contactService.fetchAllContactByAddressBook(Constants.DEFAULT_ADDRESS_BOOK);
        Assertions.assertEquals(5,defaultAddressBookUniqueContacts.size());

    }

    @Test
    @Order(9)
    void test_remove_invalid_address_book_id(){
        Assertions.assertThrows(ServiceException.class,()->contactService.fetchAllUniqueContactByAddressBook(TEST_ADDRESS_BOOK_NA));
    }
}
