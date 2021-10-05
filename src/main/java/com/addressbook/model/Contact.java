package com.addressbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    private String id;

    @NotBlank(message = "Name is mandatory")
    private String displayName;

    @NotEmpty(message = "Provide atleast one phone number")
    private List<String> phoneNumbers;

    public Contact(String displayName,List<String> phoneNumbers){
        this.displayName = displayName;
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public boolean equals(Object o) {
        Contact contact = (Contact) o;
        return Objects.equals(displayName, contact.displayName) &&
                phoneNumbers.containsAll(contact.getPhoneNumbers())
                && contact.getPhoneNumbers().containsAll(phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, phoneNumbers);
    }
}
