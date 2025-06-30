# Your Car Your Way

This application is a POC of a user/customer service chat for a car rental business.

## Back

Make sure Java is installed and check the version with `java -version`. This application requires Java 17.

### Data base

Make sure mysql is installed with `mysql -V`.  
You can use the database creation script located in `back/src/main/resources`  
Then, in your project configuration, add these environement variables and fill them according to your configuration :
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`

### Start the API

Go inside the backend folder
> cd your-car-your-way/back

Install dependencies
> mvn clean install

Start backend
> mvn spring-boot:run

The API is running on `http://localhost:8080`.  
The WebScocket is running on `http://localhost:9093`.

## Front

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 20.0.2.

### Start the application

Go inside the frontend folder
> cd your-car-your-way/front

Install dependencies
> npm install

Start frontend
> ng serve

Navigate to `http://localhost:4200/`.

## Dependencies

### Back

- Spring Web
- Spring Data JPA
- MySQL Driver
- Spring Security
- Spring Validation
- JWT
- Spring WebSocket
- Model Mapper
- Lombok

### Front

- RxJS
- Angular Router
- Angular Forms
- Tailwind
- Daisy UI