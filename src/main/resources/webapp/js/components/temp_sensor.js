import React from 'react';

export default class TempSensor extends React.Component {
    constructor() {
        super();
        this.state = {tempReadings : []};
    }

    // fetch temp readings
    componentDidMount() {
        // set a timer for refresh
        this.timerID = setInterval(
              () => this.tick(),
              1000
        );
    }

    componentWillUnmount() {
        clearInterval(this.timerID);
    }

    tick() {
        console.log("fetching new temps");
        fetch('http://localhost:4567/temperature')
            .then(result => result.json())
            .then(tempReadings => this.setState({tempReadings}));
    }

    render() {
        return (
            <div>
                <h1>Temperature Sensor Readings</h1>
                <div>
                    <ul>
                        {this.state.tempReadings.map(item => <li> {item} </li>)}
                    </ul>
                </div>
            </div>
        );
    }
}