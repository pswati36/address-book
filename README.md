# Address Book
Sprint boot application to manage contacts with in given address book.

APIs Available for below features:

1. Add contact with in given address book.
2. Remove contact by contact id with in given address book.
3. Fetch All contact by address book.
4. Fetch All unique contacts across address book.
5. Fetch All contacts across address book.

Note: 

1. Address book will be created automatically if given address book id is not exists while contact creation.
2. 

How to use:

# Build
mvn clean install

# Integration Test
mvn test

# Junit for Service layer

mvn -Dtest=ContactServiceTest test

# Start
mvn springboot:run
