import {useContext, useEffect, useState} from "react";
import {MapContext} from "../MapContext";

export default function SearchResult(props) {
    const [name, setName] = useState(props.winery.name);
    const [address, setAddress] = useState(props.winery.address);
    const [phone, setPhone] = useState(props.winery.phone);
    const [site, setSite] = useState(props.winery.site);
    const [openHours, setOpenHours] = useState(props.winery.openHours);
    const [closeHours, setCloseHours] = useState(props.winery.closeHours);
    const { highlighted, setHighlighted } = useContext(MapContext);
    const [bgColor, setBgColor] = useState("aliceblue");
    const highlight = () => {
        if (highlighted === props.winery) {
            setHighlighted(null);
        } else setHighlighted(props.winery);
    };
    useEffect(() => {
        if (highlighted === props.winery) {
            setBgColor("#d7cd80");
        } else setBgColor("aliceblue");
    }, [highlighted]);

    return (
        <div id={"result"} onClick={highlight} style={{ backgroundColor: bgColor }}>
            <h4>{name}</h4>
            <div>{address}</div>
            <div>
                {phone}
                <a href={String(site)}> {site}</a>
            </div>
            <div>
                {openHours} - {closeHours}
            </div>
        </div>
    );
}