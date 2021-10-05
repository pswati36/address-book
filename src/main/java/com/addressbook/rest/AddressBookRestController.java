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

    /**
     *  This API adds contact to given address book id. If address book id not present,
     *  new one will be created.
     * @param addressBookId - address book id.
     * @param contact - contact object to be saved.
     * @return
     */
    @PostMapping("/addressBookId/{addressBookId}")
    public Callable<Contact> addContactToAddressBook(@PathVariable String addressBookId,
                                                     @RequestBody @Valid Contact contact){
        return () ->  contactService.addContactToAddressBook(addressBookId,contact);
    }

    /**
     * Remove given contact id  from given address book id.
     * Throws Service Exception if address book id or contact is invalid.
     *
     * @param addressBookId - address book id.
     * @param contactId -  contact id.
     * @return
     */
    @DeleteMapping("/addressBookId/{addressBookId}/contactId/{contactId}")
    public Callable<Contact> removeContactFromAddressBook(@PathVariable String addressBookId,
                                                          @PathVariable String contactId){
        return () ->  contactService.removeContactFromAddressBook(addressBookId,contactId);
    }

    /**
     * This API returns all contacts with in give address book. if unique = true, fetches all unique contacts
     *  else all contacts in given address book id.
     * @param addressBookId
     * @param unique - true/false
     * @return
     */
    @GetMapping("/addressBookId/{addressBookId}/contacts")
    public Callable<List<Contact>> fetchAllContactByAddressBook(@PathVariable String addressBookId,
                                                                @RequestParam(required = false) boolean unique){
        return () -> {
            if(unique){
                return contactService.fetchAllUniqueContactByAddressBook(addressBookId);
            } else {
                return contactService.fetchAllContactByAddressBook(addressBookId);
            }
        };
    }

    /**
     *  This API returns all contacts across address book. if unique = true, fetches all unique contacts
     *  else all contacts.
     * @param unique
     * @return
     */
    @GetMapping("contacts")
    public Callable<List<Contact>> fetchAllContacts(@RequestParam(required = false) boolean unique){
        return () -> {
            if(unique) {
                return contactService.fetchAllUniqueContacts();
            } else {
                return contactService.fetchAllContacts();
            }
        };
    }

}
