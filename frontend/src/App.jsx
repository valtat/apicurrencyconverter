import "./App.css";
import CurrencyRow from "./components/CurrencyRow.jsx";
import CurrencyConverterText from "./components/CurrencyConverterText.jsx";
import TimeStamp from "./components/TimeStamp.jsx";
import ConvertButton from "./components/ConvertButton.jsx";
import useFetch from "./hooks/useFetch.jsx";
import React, { useState, useEffect } from "react";
import ResultText from "./components/ResultText.jsx";

const BASE_URL = "http://localhost:8080/api/currency";
const CONVERT_URL = "http://localhost:8080/api/change-currency";

//"https://apicurrencyconverter-hndhcrcaczayhudr.northeurope-01.azurewebsites.net/api/currency"
//"https://apicurrencyconverter-hndhcrcaczayhudr.northeurope-01.azurewebsites.net/api/change-currency"

function App() {
  const [currencies, setCurrencies] = useState([]);
  const [fromCurrency, setFromCurrency] = useState("");
  const [toCurrency, setToCurrency] = useState("");

  const [conversionAmount, setConversionAmount] = useState("");
  const [conversionFromCurrency, setConversionFromCurrency] = useState("");
  const [conversionToCurrency, setConversionToCurrency] = useState("");

  const [amount, setAmount] = useState("");
  const [convertedAmount, setConvertedAmount] = useState("");

  const { timestamp, currencyOptions } = useFetch(BASE_URL);

  useEffect(() => {
    fetch(BASE_URL)
      .then((res) => res.json())
      .then((data) => {
        setCurrencies(Object.keys(data.rates));
        setFromCurrency(data.base);
        setToCurrency(Object.keys(data.rates)[0]);
        setAmount(1);
      })
      .catch((error) => console.error("Error fetching currencies:", error));
  }, []);

  const handleConvert = () => {
    if (amount && fromCurrency && toCurrency) {
      const url = `${CONVERT_URL}?amount=${amount}&from=${fromCurrency}&to=${toCurrency}`;
      fetch(url)
        .then((res) => res.json())
        .then((data) => {
          setConvertedAmount(data);
        })
        .catch((error) => console.error("Error converting currency:", error));
      setConversionAmount(amount);
      setConversionFromCurrency(fromCurrency);
      setConversionToCurrency(toCurrency);
    } else {
      console.error("Amount, fromCurrency, and toCurrency must be provided");
    }
  };

  function handleFromAmountChange(e) {
    const newValue = e.target.value.replace(/[^0-9.]/g, "");

    if (newValue.length > 11) {
      return;
    }

    if (newValue === "" || !/^\d+(\.\d+)?$/.test(newValue)) {
      return;
    }

    setAmount(newValue);
  }

  return (
    <>
      <div className="container">
        <CurrencyConverterText />
        <CurrencyRow
          currencyOptions={currencyOptions}
          selectedCurrency1={fromCurrency}
          onChangeCurrency1={(e) => setFromCurrency(e.target.value)}
          selectedCurrency2={toCurrency}
          onChangeCurrency2={(e) => setToCurrency(e.target.value)}
          onChangeAmount={handleFromAmountChange}
          amount={amount}
        />
        <ConvertButton onClick={handleConvert} />
        <ResultText
          amount={conversionAmount}
          fromCurrency={conversionFromCurrency}
          toCurrency={conversionToCurrency}
          result={convertedAmount}
        />
        <TimeStamp timestamp={timestamp} />
      </div>
    </>
  );
}

export default App;
