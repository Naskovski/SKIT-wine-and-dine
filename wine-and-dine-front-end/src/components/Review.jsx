import "./Review.css";
import { useContext, useEffect, useState } from "react";

export default function Review(props) {

    const timestamp = new Date(props.review.timestamp);

    console.log(props)
    return (
        <div id={"Review"}>
            <div>
                <div className={'horizontal-flex'} style={{justifyContent: "space-between"}}>
                    <h1 style={{fontWeight: "lighter"}}><b>{props.review.score}</b>/5</h1>
                    <span>{props.review.createdBy!=undefined && props.review.createdBy.firstName}</span>
                </div>

                <h4>{timestamp.toLocaleDateString("mk-MK", { weekday: 'long', year: 'numeric', month: 'short', day: 'numeric' })}</h4>

            </div>
            <p className={'description'}>{props.review.description}</p>
        </div>
    );
}
