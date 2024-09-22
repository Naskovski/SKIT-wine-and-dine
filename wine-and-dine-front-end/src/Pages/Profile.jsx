import "./Profile.css";
import {Navigate, useNavigate} from "react-router-dom";
import {useContext, useEffect, useState} from "react";
import { AuthContext } from "../AuthContext";
import {jwtDecode} from "jwt-decode";

const Profile = () => {
  const {user, setUser} = useContext(AuthContext);
  const navigate = useNavigate();
  const [userData, setUserData] = useState(null);

  const logout = () =>{
    localStorage.removeItem("accessToken");
    setUser(null);
  }

  useEffect(() => {
    if(!user) navigate("/login");
    else{
        console.log(user)
        console.log(jwtDecode(user))
        setUser(localStorage.getItem("accessToken"))
        setUserData(jwtDecode(user));
    }
    console.log(userData)
  }, [user]);

  return (<div className="profile horizontal-flex">
            <div className={'vertical-flex'} style={{textAlign: "center"}}>
                <h3>Профил</h3>
                <div className="profile-info">
                    <h1>{userData && userData.firstName}</h1>
                    <p>Емаил: {userData && userData.sub}</p>
                </div>
                <button onClick={logout}>Одјава</button>
            </div>

          </div>
  );
};

export default Profile;
