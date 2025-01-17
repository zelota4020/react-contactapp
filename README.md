# --= contact app (backend)=-- 

This is an example project to demonstrate how to use... 

This is my first Kotlin project.

### Docker

create postgres container:
<br/>```docker run --name contactapi -p 5432:5432 -e POSTGRES_PASSWORD=Password123. -e POSTGRES_USER=contact-user -e POSTGRES_DB=contactdb -d postgres -c max_prepared_transactions=200```

run `create-table.sql` from resouces/SQL folder.

### How to build and run

compile/build: ```mvn clean package```

run: ```mvn spring-boot:run```

### Source

This project is based on: https://www.youtube.com/watch?v=-LUA-LHXobE
<br/>The source example ReactJS repo    : https://github.com/getarrays/contactapp
<br/>The source example Spring Boot repo: https://github.com/getarrays/contactapi
<br/>Thanks for "Get Arrays" channel for the tutorial.

### Frontend

see `ProjectFiles/contact-app/README.md`

### Other

About CORS: https://www.dhiwise.com/post/cors-in-react-essential-techniques-for-web-developers
