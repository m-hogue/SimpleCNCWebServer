# SimpleCNCWebServer

A simple sparkjava (jetty) web server which listens for sensor readings expected to be POSTed from Arduino clients. Sensor readings will be displayed @ http://localhost:9090/index.html for now.

 
### Temperature Readings
The REST endpoint looks like so:

`$ curl http://localhost:9090/temperature/tempSensor1?tempReading=45.0`

We care about sensor name and the actual reading. There's currently no validation on the temperature reading.
 
