import "./Searchbar.css";
import { useContext, useEffect, useState } from "react";
import { MapContext } from "../MapContext";
import image from "../images/search-icon.png";

export default function Searchbar() {
  const { wineries, setWineries } = useContext(MapContext);
  const [searchTerm, setSearchTerm] = useState("");

  const clear = () => {
    setSearchTerm("");
    search();
  };
  useEffect(() => {
    search();
  }, []);

  async function search() {
    const response = await fetch(
      "http://localhost:3005/api/search?query=" + searchTerm
    );
    const data = await response.json();
    setWineries(data);
  }

  return (
    <div id={"Search"}>
      <input
        type={"text"}
        name={"winery-search"}
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
      />
      <button className="search-button" onClick={() => search()}>
        <img src={image} alt="search icon" />
      </button>
      <button className="clear-button" onClick={clear}>
        X
      </button>
    </div>
  );
}
