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
2. Contact will be considered as same if name and all phone numbers matches.
3. All data validation done at controller layer. We can extend to service layer if required.

Further Improvement:

1. Can add spring security for user & role management and context to track user level activities.
2. Can move to persistent storage like mongo and mariadb.

How to use:

# Build
mvn clean install

# Integration Test
mvn test

# Junit for Service layer

mvn -Dtest=ContactServiceTest test

# Start
mvn springboot:run

Once run refer to below url for API documentation.

http://${host}:${serverPort}/swagger-ui/index.html

# Dockerization info

Dockerfile committed has all steps including health check. Please update serverPort accordingly.

Please run below command (update repository and port details as per your requirements)

# docker build -t springio/gs-spring-boot-docker .
# docker run -p 8989:8989 -e serverPort=8989 springio/gs-spring-boot-docker 


