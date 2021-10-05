package com.addressbook.rest;

import com.addressbook.model.Contact;
import com.addressbook.service.ContactService;
import com.addressbook.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(Constants.addressBookMapping)
public class AddressBookRestController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/addressBookId/{addressBookId}")
    public Callable<Contact> addContactToAddressBook(@PathVariable String addressBookId,
                                                     @RequestBody @Valid Contact contact){
        return () ->  contactService.addContactToAddressBook(addressBookId,contact);
    }

    @DeleteMapping("/addressBookId/{addressBookId}/contactId/{contactId}")
    public Callable<Contact> removeContactFromAddressBook(@PathVariable String addressBookId,
                                                          @PathVariable String contactId){
        return () ->  contactService.removeContactFromAddressBook(addressBookId,contactId);
    }

    @GetMapping("/addressBookId/{addressBookId}/contacts")
    public Callable<List<Contact>> fetchAllContactByAddressBook(@PathVariable String addressBookId){
        return () -> contactService.fetchAllContactByAddressBook(addressBookId);
    }

    @GetMapping("contacts")
    public Callable<List<Contact>> fetchAllContactByAddressBook(@RequestParam boolean unique){
        return () -> {
            if(unique) {
                return contactService.fetchAllUniqueContacts();
            } else {
                return contactService.fetchAllContacts();
            }
        };
    }

}
