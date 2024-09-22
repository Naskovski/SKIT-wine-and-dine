import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../AuthContext";
import { useNavigate } from "react-router-dom";
import "./Login.css";

function Login() {
  const {user, setUser} = useContext(AuthContext);
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    if (user) {
      navigate("/profile");
    }
  }, [user]);

  function handleKeyUp(event, action) {
    if (event.key === "Enter") {
      if (action === "Confirm") {
        logInWithEmailAndPassword(email, password);
      }
      if (action === "Next") {
        document.getElementById("loginPasswordInput").focus();
      }
    }
  }

  async function logInWithEmailAndPassword(email, password) {
    try {
      const response = await fetch('/api/auth/login', {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const data = await response.json();
        const token = data.token;
        localStorage.setItem("accessToken", token);
        setUser(token);
        alert("Успешно се најавивте!");
        navigate("/profile");
      } else {
        alert("Неуспешна најава! Обидете се повторно.");
      }
    } catch (error) {
      console.error("Грешка при најава: ", error);
    }
  }

  return (
    <div className={"loginWrapper"}>
      <div id={"login"} className={"login"}>
        <h2>Најава</h2>
        <div id={"inputFields"}>
          <input
            placeholder={"Email"}
            type={"email"}
            value={email}
            onKeyUp={(e) => handleKeyUp(e, "Next")}
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            id={"loginPasswordInput"}
            placeholder={"Password"}
            type={"password"}
            value={password}
            onKeyUp={(e) => handleKeyUp(e, "Confirm")}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button
            className="rec_button accent"
            onClick={() => logInWithEmailAndPassword(email, password)}
          >
            Најава
          </button>
        </div>
        <div>
          <a href="/register" className="register-button">
            Регистрирај се
          </a>
        </div>
      </div>
    </div>
  );
}

export default Login;
