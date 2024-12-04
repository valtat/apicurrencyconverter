# Welcome to APICurrencyConverter project

This is a Currency Converter application that allows the user to convert a variety of currencies with the latest rates updated daily.

The application is running here: https://valeriot.z16.web.core.windows.net/

Security for the app is being maintained with dependabot in order to find out potential security threats.

_Disclaimer: the app is on a free tier of Azure, so if the app is asleep please refresh the page and wait for the Currency Data to be loaded._

## How does it work?

The frontend is created with React, and it calls the backend api server with the amount and currencies to be converted.

The backend is created using Java Spring boot. It gets the currency data from an external api called Exchange Rates Api once per day and stores it to MongoDB. When the conversion is being calculated, the backend server first checks MongoDB for the currency rates. If MongoDB is empty, the backend calls Exchange Rates Api and stores the data.

![apicurrencyconverter.png](https://github.com/valtat/apicurrencyconverter/blob/main/apicurrencyconverter.png)
_Architectural design of the application_

## How to run it locally

Feel free to copy the repository and try it out locally. Things to keep in mind:

- Run npm install while in the frontend folder to install necessary dependencies
- The default addresses for api calls are "http://localhost:8080/api/currency" and "http://localhost:8080/api/change-currency". Change port numbers and @CrossOrigin annotation in CurrencyDataController.java if needed
- Launch the frontend app with npm run dev while in the frontend folder
- Create a folder called resources in the /src/main folder located in the backend folder, create a file called application.properties and add your MongoDB uri and Exchange Rates Api key environment variables
- If you need to use another port for the frontend update vite.config.js file in the frontend folder like this:
  server: {
  port: 3006,
  },
- Run the backend server locally by starting ApicurrencyconverterApplication.java

Example application.resources:

    spring.data.mongodb.uri=your_mongodb_uri
    EXCHANGE_RATES_API_KEY=your_api_key
    server.port=8081   //this is how you change the default port in Spring Boot

_Stay tuned for upcoming Docker compose that will run the app locally without the need for api key or mongodb cloud..._

### The idea behind the project

I created this project in order to learn more about Full Stack development. I wanted to build a whole application by myself, including the frontend and backend. In the end, I was able to learn how to use Spring Boot, how to create my own api server and how to deploy it to a cloud (Microsoft Azure in this case) with a deployment pipeline. Finally, I learned how to build a static website that is also accessible from Azure.
