# Welcome to ApiCurrencyConverter project

This is a Currency Converter application that allows the user to convert a variety of currencies with the latest rates updated daily.

The application is running here: https://valeriot.z16.web.core.windows.net/

## How to use it?
Add the amount you want to convert into the Input field that has "Amount" written on top of it. It has to be a positive integer with a maximum length of 11 characters.
Next, choose the currency you want to convert from and the currency you want to convert to. The available currencies are in the drop down menus with "From" and "To" texts on top of them.
Finally, click the Convert button to see the result of the conversion.

If the drop down menus do not have any currencies to choose from, _please wait for the Currency Data to be loaded or try refreshing the page_.

## How does it work?
The frontend is created with React. It sends requests to the applications backend server which is deployed to Microsoft Azure. First, the frontend calls the /currency endpoint when the page is loaded.
The endpoint provides the frontend with the available currencies. When the user converts currencies, the Frontend calls the /change-currency endpoint and provides the amount, the from currency and
the to currency to the backend as parameters. The backend then calculates the conversion and returns the result to the Frontend.

Example conversion fetch request:

    const url = `${CONVERT_URL}?amount=${amount}&from=${fromCurrency}&to=${toCurrency}`;
      fetch(url)
        .then((res) => res.json())
        .then((data) => {
          setConvertedAmount(data);
        })

The backend is created using Java Spring boot. It gets the currency data from an external api called Exchange Rates Api once per day and stores it to MongoDB. When the conversion is being calculated,
the backend server first checks MongoDB for the currency rates. If MongoDB is empty, the backend calls Exchange Rates Api and stores the data.

![apicurrencyconverter_architecture](https://github.com/user-attachments/assets/cece2619-3001-4aab-8b3e-af8eec88b3ac)
_Architectural design of the application_

## Can you run it locally?

Yes, absolutely. Feel free to copy the repository and try it out locally. Things to keep in mind:

  - After you have copied the project, navigate to the frontend folder in your terminal. Run the command npm install while in the folder to install the necessary dependencies.
  - In the App.jsx file in the frontend, you need to specify the correct urls for the backend server. The default address is "http://localhost:8080/api/currency" and "http://localhost:8080/api/change-currency"
    but if you cannot use the port 8080, change it here and set a new port for Spring Boot.
  - Run the frontend app with npm run dev
  - In the backend folder, you need to create a folder called resources in the /src/main folder, create a file called application.properties and add your MongoDB uri and Exchange Rates Api key environment variables. Name the variables like this: spring.data.mongodb.uri and EXCHANGE_RATES_API_KEY. Without these, the app will not work.
  - Open the file CurrencyDataController.java and update the @CrossOrigin annotation if needed. The default port that the frontend uses is 5173, but if you need to use another then you need to change this. To change the port the frontend uses, add this line to the vite.config.js file in the frontend folder: server: {
    port: 3006,
  },
  - Run the backend server locally by starting ApicurrencyconverterApplication.java

Example application.resources:

    spring.data.mongodb.uri=your_mongodb_uri
    EXCHANGE_RATES_API_KEY=your_api_key
    server.port=8081   //this is how you change the default port in Spring Boot

        

### The idea behind the project
I created this project in order to learn more about Full Stack development. I wanted to build a whole application by myself, including the frontend and backend. In the end, I was able to learn
how to use Spring Boot and how to create my own api server. I also learned how to deploy a project to Microsoft Azure, which is a very important skill. Finally, I learned how to build a static website
that is also accessible from Azure.
