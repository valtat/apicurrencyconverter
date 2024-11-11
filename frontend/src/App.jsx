import "./App.css";
import CurrencyRow from "./components/CurrencyRow.jsx";
import CurrencyConverterText from "./components/CurrencyConverterText.jsx";
import TimeStamp from "./components/TimeStamp.jsx";
import ConvertButton from "./components/ConvertButton.jsx";
import useFetch from "./hooks/useFetch.jsx";
import useCurrencyConverter from "./hooks/useCurrencyConverter";
import ResultText from "./components/ResultText.jsx";

const BASE_URL = "http://localhost:8080/api/currency";
const CONVERT_URL = "http://localhost:8080/api/change-currency";

//"https://apicurrencyconverter-hndhcrcaczayhudr.northeurope-01.azurewebsites.net/api/currency"
//"https://apicurrencyconverter-hndhcrcaczayhudr.northeurope-01.azurewebsites.net/api/change-currency"

function App() {
  const {
    timestamp,
    currencyOptions,
    fromCurrency,
    toCurrency,
    setFromCurrency,
    setToCurrency,
    amount,
    setAmount,
  } = useFetch(BASE_URL);

  const {
    handleConvert,
    convertedAmount,
    conversionAmount,
    conversionFromCurrency,
    conversionToCurrency,
  } = useCurrencyConverter(fromCurrency, toCurrency, CONVERT_URL, amount);

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
