import React, { useState, createContext } from 'react';

const MapContext = createContext();

const MapDataProvider = ({ children }) => {
    const [latitude, setLatitude] = useState(41.0);
    const [longitude, setLongitude] = useState(21.0);
    const [highlighted, setHighlighted] = useState(null);
    const [wineries, setWineries] = useState([]);
    const [position, setPosition] = useState(null);
    const [displayRoute, setDisplayRoute] = useState(false);

    const value= {
        latitude, setLatitude,
        longitude, setLongitude,
        wineries, setWineries,
        highlighted, setHighlighted,
        position, setPosition,
        displayRoute, setDisplayRoute
    };

    return (
        <MapContext.Provider value={value}>
            {children}
        </MapContext.Provider>
    );
}
export { MapContext, MapDataProvider };