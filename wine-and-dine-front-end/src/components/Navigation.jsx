import React from "react";
import { Link } from "react-router-dom";
import "./Navigation.css";

const Navigation = () => {
  return (
    <div className="nav">
      <Link to="/" className="link">
        Почетна
      </Link>
      <Link to="/about" className="link">
        За нас
      </Link>
      <Link to="/contact" className="link">
        Контакт
      </Link>
      <Link to="/profile" className="link">
        Профил
      </Link>
    </div>
  );
};

export default Navigation;
