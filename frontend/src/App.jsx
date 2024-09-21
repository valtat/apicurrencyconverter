import "./App.css";
import CurrencyRow from "./components/CurrencyRow.jsx";
import CurrencyConverterText from "./components/CurrencyConverterText.jsx";
import TimeStamp from "./components/TimeStamp.jsx";
import useFetch from "./hooks/useFetch.jsx";
import React, { useState, useEffect } from "react";

const BASE_URL = "http://localhost:8080/api/currency";
const CONVERT_URL = "http://localhost:8080/api/change-currency";

//"https://apicurrencyconverter-hndhcrcaczayhudr.northeurope-01.azurewebsites.net/api/currency"
//"https://apicurrencyconverter-hndhcrcaczayhudr.northeurope-01.azurewebsites.net/api/change-currency"

function App() {
  const [amount, setAmount] = useState(1);
  const [isAmountInFromCurrency, setAmountInFromCurrency] = useState(true);
  const [exchangeRate, setExchangeRate] = useState(1);

  const {
    currencies,
    timestamp,
    currencyOptions,
    fromCurrency,
    toCurrency,
    setFromCurrency,
    setToCurrency,
  } = useFetch(BASE_URL);

  useEffect(() => {
    if (fromCurrency && toCurrency) {
      fetchExchangeRate(fromCurrency, toCurrency);
    }
  }, [fromCurrency, toCurrency]);

  const fetchExchangeRate = async (from, to) => {
    try {
      const response = await fetch(`${CONVERT_URL}?amount=1&from=${from}&to=${to}`);
      const rate = await response.json();
      setExchangeRate(rate);
    } catch (error) {
      console.error("Error fetching exchange rate:", error);
    }
  };

  let inputAmount, outputAmount;
  if (isAmountInFromCurrency) {
    outputAmount = amount;
    inputAmount = amount * exchangeRate;
  } else {
    inputAmount = amount;
    outputAmount = amount / exchangeRate;
  }

  function handleFromAmountChange(e) {
    setAmount(e.target.value);
    setAmountInFromCurrency(true);
  }

  function handleToAmountChange(e) {
    setAmount(e.target.value);
    setAmountInFromCurrency(false);
  }

  const handleFromCurrencyChange = (e) => {
    const newCurrency = e.target.value;
    setFromCurrency(newCurrency);
  };

  const handleToCurrencyChange = (e) => {
    const newCurrency = e.target.value;
    setToCurrency(newCurrency);
  };

  return (
    <>
      <div className="container">
        <CurrencyConverterText />
        <CurrencyRow
          currencyOptions={currencyOptions}
          selectedCurrency={fromCurrency}
          onChangeCurrency={handleFromCurrencyChange}
          onChangeAmount={handleFromAmountChange}
          amount={outputAmount}
        />
        <div className="equals">=</div>
        <CurrencyRow
          currencyOptions={currencyOptions}
          selectedCurrency={toCurrency}
          onChangeCurrency={handleToCurrencyChange}
          onChangeAmount={handleToAmountChange}
          amount={inputAmount}
        />
        <TimeStamp timestamp={timestamp} />
      </div>
    </>
  );
}

export default App;