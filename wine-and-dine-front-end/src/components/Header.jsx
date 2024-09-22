import Navigation from "./Navigation";
import image from "../images/logo.png";
import "./Header.css";

const Header = () => {
  return (
    <div className="header">
      <img src={image} alt="wine and dine logo" />
      <Navigation />
    </div>
  );
};

export default Header;
