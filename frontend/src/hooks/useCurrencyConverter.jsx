import { useState } from "react";

const useCurrencyConverter = (
  fromCurrency,
  toCurrency,
  CONVERT_URL,
  amount
) => {
  const [convertedAmount, setConvertedAmount] = useState(null);
  const [conversionAmount, setConversionAmount] = useState(null);
  const [conversionFromCurrency, setConversionFromCurrency] = useState(null);
  const [conversionToCurrency, setConversionToCurrency] = useState(null);

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

  return {
    handleConvert,
    convertedAmount,
    conversionAmount,
    conversionFromCurrency,
    conversionToCurrency,
  };
};

export default useCurrencyConverter;
