import React from "react";

export default function ResultText({
  fromCurrency = "",
  toCurrency = "",
  amount = "",
  result = "",
}) {
  return (
    <>
      <label className="result">
        {amount && fromCurrency && toCurrency && result
          ? `${amount} ${fromCurrency} is equal to ${result} ${toCurrency}`
          : ""}
      </label>
    </>
  );
}
