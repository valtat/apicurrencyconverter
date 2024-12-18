import React from "react";

export default function TimeStamp({ timestamp }) {
  return (
    <>
      {timestamp ? (
        <label className="timestamp">
          Currencies updated on: {new Date(timestamp * 1000).toLocaleString()}
        </label>
      ) : (
        <label className="timestamp">Loading data. Please be patient. You may need to refresh the page.</label>
      )}
    </>
  );
}
