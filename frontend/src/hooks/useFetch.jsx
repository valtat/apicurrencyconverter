import { useState, useEffect } from "react";

const useFetch = (BASE_URL) => {
  const [, setCurrencies] = useState([]);
  const [timestamp, setTimestamp] = useState("");
  const [currencyOptions, setCurrencyOptions] = useState([]);
  const [fromCurrency, setFromCurrency] = useState("");
  const [toCurrency, setToCurrency] = useState("");
  const [amount, setAmount] = useState(1);

  useEffect(() => {
    fetch(BASE_URL)
      .then((res) => res.json())
      .then((data) => {
        setCurrencies(Object.keys(data.rates));
        setTimestamp(data.timestamp);
        const initialCurrency = Object.keys(data.rates)[0];
        setCurrencyOptions([data.base, ...Object.keys(data.rates)]);
        setFromCurrency(data.base);
        setToCurrency(initialCurrency);
        setAmount(1);
      })
      .catch((error) => console.error("Error fetching currencies:", error));
  }, [BASE_URL]);

  return {
    timestamp,
    currencyOptions,
    fromCurrency,
    toCurrency,
    setFromCurrency,
    setToCurrency,
    amount,
    setAmount,
  };
};

export default useFetch;
