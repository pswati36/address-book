package com.addressbook.service;

import com.addressbook.exception.ServiceException;
import com.addressbook.model.Contact;
import com.addressbook.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactService {

    private final Map<String, List<Contact>> addressBooks = new HashMap<>();

    public  ContactService(){
        addressBooks.put(Constants.DEFAULT_ADDRESS_BOOK,new ArrayList<>());

        this.addContactToAddressBook(Constants.DEFAULT_ADDRESS_BOOK,new Contact("Will Smith", Arrays.asList("0142292158")));
    }

    public Contact addContactToAddressBook(String addressBookId,Contact contact){
        contact.setId(UUID.randomUUID().toString());
        if (!addressBooks.containsKey(addressBookId)) {
            addressBooks.put(addressBookId, new ArrayList<>());
        }
        addressBooks.get(addressBookId).add(contact);
        return contact;
    }

    public Contact removeContactFromAddressBook(String addressBookId, String contactId){
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ServiceException("AddressBook with the given Id does not exist");
        }
        Optional<Contact> contactToBeRemoved = addressBooks.get(addressBookId).stream()
                .filter(contact -> contact.getId().equals(contactId)).findFirst();
        if (contactToBeRemoved.isPresent()) {
            addressBooks.get(addressBookId).remove(contactToBeRemoved.get());
            return contactToBeRemoved.get();
        } else {
            throw new ServiceException("The given contact does not exist!");
        }
    }

    public List<Contact> fetchAllContactByAddressBook(String addressBookId){
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ServiceException("AddressBook with the given Id does not exist");
        }
        return addressBooks.get(addressBookId);
    }

    public List<Contact> fetchAllUniqueContactByAddressBook(String addressBookId){
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ServiceException("AddressBook with the given Id does not exist");
        }
        return addressBooks.get(addressBookId).stream().distinct().collect(Collectors.toList());
    }

    public List<Contact> fetchAllContacts(){
        List<Contact> contacts = new ArrayList<>();
        addressBooks.values().forEach(contacts::addAll);
        return contacts;

    }

    public List<Contact> fetchAllUniqueContacts(){
        return this.fetchAllContacts().stream().distinct().collect(Collectors.toList());
    }

}
