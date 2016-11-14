# SimpleCNCWebServer

A simple sparkjava (jetty) web server which listens for sensor readings expected to be POSTed from Arduino clients.

 
### Temperature Readings
The REST endpoints looks like so:

#### Posting a temperature reading
`$ curl -X POST http://localhost:4567/temperature/:sensorName?tempReading=45.0`

#### Retrieving temperature readings for a particular sensor
`$ curl http://localhost:4567/temperature/:sensorName`

#### Retrieving all temperature sensor readings
`$ curl http://localhost:4567/temperature`

We care about sensor name and the actual reading. There's currently no validation on the temperature reading. Right now, read times are set by the server. We can alter this behavior if necessary.
 
### Technologies
 - Web
   + Boostrap
   + ReactJS
 - Server
   + SparkJava Web Server
   + Typesafe Config
   + RestAssured
 - Other
   + Gulp 
   + NPM [Node Package Manager]
