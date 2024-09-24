import "./Home.css";
import Map from "../components/Map";
import Searchbar from "../components/Searchbar";
import SearchResultsContainer from "../components/SearchResultsContainer";
import {MapContext, MapDataProvider} from "../MapContext";
import {useContext, useEffect, useState} from "react";
import Review from "../components/Review";
import {AuthContext} from "../AuthContext";
import {jwtDecode} from "jwt-decode";

const Home = () => {
    const {highlighted} = useContext(MapContext);
    const {position} = useContext(MapContext);
    const {displayRoute, setDisplayRoute} = useContext(MapContext);

    const {user} = useContext(AuthContext);

    const [display, setDisplay] = useState(null);
    const [reviewData, setReviewData] = useState([]);
    const [reviewScore, setReviewScore] = useState(0);
    const [newReviewDesc, setNewReviewDesc] = useState("");
    const [newReviewScore, setNewReviewScore] = useState(0);

    const [errorMessage, setErrorMessage] = useState('');


    useEffect(() => {
        if(highlighted==null) setDisplay(null);
    }, [highlighted]);

    const toggleReview = () => {
        if(highlighted!=null) {
            fetchReviews().then(setDisplay("read"));
            fetchScore();
        }
    };

    const toggleRoute = () => {
        if(highlighted!=null) {
            if(position!=null){
                setDisplayRoute(!displayRoute);
                console.log('displayRoute: ', displayRoute);
                setDisplay("route")
            }else{
                setDisplayRoute(false);
                alert("Мора да дозволите пристап до вашата локација, за да добиете рута до избраната винарија.")
            }
        }else {
            setDisplayRoute(false);
        }
    };

    async function fetchReviews() {
        const response = await fetch(
            "http://localhost:3000/api/review/all/" + highlighted.id
        );
        const data = await response.json();
        setReviewData(data);
    }

    async function fetchScore() {
        const response = await fetch(
            "http://localhost:3000/api/review/score/" + highlighted.id
        );
        const data = await response.json();
        setReviewScore(data);
    }

    async function addReview(){
        try {
            const response = await fetch("/api/review/add/" + highlighted.id, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    "desc": newReviewDesc,
                    "score": Number(newReviewScore),
                    "timestamp": new Date().toISOString(),
                    "userId": jwtDecode(user).id
                }),
            });

            if (response.ok) {
                fetchReviews();
                fetchScore();
                setDisplay("read");
            } else if (response.status === 400){
                console.error("Bad request:", response.body);
                setErrorMessage("Некоректно мислење, за високата оценка треба да внесете повеќе опис, а за ниската оценка не треба да го користите зборот одлично");
                setTimeout(() => {
                    setErrorMessage("");
                }, 5000);
            } else {
                console.error("Server side error adding review:", response.statusText);
            }
        } catch (error) {
            console.error("Error adding review:", error);
        }
        setNewReviewScore(3);
        setNewReviewDesc("");
    }

    return (
        <div className="home">
            <Searchbar />
            <div className={"horizontal-flex"}>
                <Map />
                <SearchResultsContainer />
            </div>
            <div className="buttons">
                <button disabled={highlighted == null}
                        onClick={toggleRoute}
                >Добиј Рута</button>|
                <button disabled={highlighted == null}
                        onClick={toggleReview}
                >Читај Мислење</button>|
                <button disabled={user == null || highlighted == null}
                        onClick={()=>setDisplay("add")}
                >Додади Мислење</button>
            </div>
            {errorMessage !== "" && <div className={"error-message"}>{errorMessage}</div>}
            {display==="read" && reviewData.length===0 && <h3 style={{textAlign: "center"}}> За жал, нема внесени мислења за избраната винарија</h3>}
            {display==="read" && reviewData.length>0 && <div style={{padding: "2rem"}} className={'review-section'}>

                <h3>Просечна оценка: {reviewScore.toFixed(2)}</h3>

                <div className={'horizontal-flex review-container'}>
                    {reviewData.length>0 && reviewData.map(rev => <Review review={rev} key={rev.id}/>) /*mora dvojna proverka*/}
                </div>

            </div>}

            {display==="add" && <div style={{padding: "2rem"}} className={'review-section vertical-flex'}>
                <label>
                    <input type={'number'}
                           min={"1"}
                           max={"5"}
                           value={newReviewScore}
                           onChange={(e)=>setNewReviewScore(e.target.value)}
                    />
                    Оцена
                </label>

                <label>
                <textarea
                    name={'desc'}
                    value={newReviewDesc}
                    onChange={(e)=>setNewReviewDesc(e.target.value)}
                />
                    Мислење
                </label>

                <button onClick={() => addReview()}>Внеси</button>
            </div>}


        </div>
    );
};

export default Home;
