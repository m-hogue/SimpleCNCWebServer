import React from 'react';

export default class TempSensorTable extends React.Component {
    constructor() {
        super();
        this.state = {tempReadings: []};
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
        fetch('http://localhost:4567/temperature?limit=30')
            .then(result => result.json())
            .then(tempReadings => this.setState({tempReadings}));
    }

    render() {
        return (
            <div className="panel panel-primary">
              <div className="panel-heading">Last 30 Temperature Readings</div>
              <div className="panel-body">
                <p>
                    Below are an auto-refreshing list of the last 30 temperature readings.
                </p>
              </div>
              <table className="table table-striped">
                <th>Sensor Name</th>
                <th>Temperature Reading</th>
                <th>Read Date</th>
                {this.state.tempReadings.map((item, key) => {
                    return (
                        <tr key={key}>
                            <td>{item.sensorName}</td>
                            <td>{item.tempReading}</td>
                            <td>{item.readTime}</td>
                        </tr>
                    )
                 })}
              </table>
            </div>
        );
    }
}