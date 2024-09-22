import "./SearchResultsContainer.css";
import {useContext, useEffect} from "react";
import {MapContext} from "../MapContext";
import SearchResult from "./SearchResult";

export default function SearchResultsContainer() {
    const {wineries} = useContext(MapContext);

    useEffect(() => {
        console.log(wineries)
    }, [wineries])

    return (
        <div id={"resultsContainer"}>

            {wineries.length === 0 &&
                <div style={{textAlign: "center", padding: "1rem"}}>
                    No results match your query
                </div>}
            {wineries.map(winery => (
                <SearchResult key={winery.id} winery={winery}/>
            ))}
        </div>
    );
}
