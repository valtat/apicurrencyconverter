import React from "react";

export default function CurrencyRow({
  currencyOptions,
  selectedCurrency1,
  onChangeCurrency1,
  selectedCurrency2,
  onChangeCurrency2,
  onChangeAmount,
  amount,
}) {
  return (
    <div className="row">
      <div className="input-container">
        <label htmlFor="amountInput">Amount:</label>
        <input
          type="number"
          className="input"
          value={amount}
          onChange={onChangeAmount}
          id="amountInput"
        />
      </div>

      <div className="select-container">
        <div>
          <label htmlFor="fromCurrencySelect">From:</label>
          <select
            className="value-box"
            value={selectedCurrency1}
            onChange={onChangeCurrency1}
            id="fromCurrencySelect"
          >
            {currencyOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="toCurrencySelect">To:</label>
          <select
            className="value-box"
            value={selectedCurrency2}
            onChange={onChangeCurrency2}
            id="toCurrencySelect"
          >
            {currencyOptions.map((option) => (
              <option key={option} value={option}>
                {option}
              </option>
            ))}
          </select>
        </div>
      </div>
    </div>
  );
}
