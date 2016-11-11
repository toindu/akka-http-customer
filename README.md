# Quiz Management Service
Tutorial on how to build a REST CRUD application with Akka-Http

Article: http://danielasfregola.com/2016/02/07/how-to-build-a-rest-api-with-akka-http/

## How to run the service
Clone the repository:
```
> git clone https://github.com/DanielaSfregola/quiz-management-service.git
```

Get to the `akka-http-crud` folder:
```
> cd akka-http-crud
```

Run the service:
```
> sbt run
```

The service runs on port 5000 by default.

## Usage

Question entity:
```
case class Question(id: String, title: String, text: String)
```

### Create a question
Request:
```
curl -v -H "Content-Type: application/json" \
	 -X POST http://localhost:5000/questions \
	 -d '{"id": "test", "title": "MyTitle", "text":"The text of my question"}'


	 curl -v -H "Content-Type: application/json" \
     	 -X POST http://localhost:5000/customers \
     	 -d '{"id": "sbc123", "name": "Indrajit Banerjee", "mobile":123456}'

```
Response if the question has been created:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> POST /questions HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 68
> 
* upload completely sent off: 68 out of 68 bytes
< HTTP/1.1 201 Created
< Location: http://localhost:5000/questions/test
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:16:50 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact

```
Response if the question with the specified id already exists:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> POST /questions HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 68
> 
* upload completely sent off: 68 out of 68 bytes
< HTTP/1.1 409 Conflict
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:17:07 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact
```


### Get a question
Request:
```
curl -v http://localhost:5000/questions/test

curl -v http://localhost:5000/customers/test
curl -v http://localhost:5000/customers/sbc123
```
Response if the question exists:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> GET /questions/test HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:17:31 GMT
< Content-Type: application/json
< Content-Length: 64
< 
* Connection #0 to host localhost left intact
{"id":"test","title":"MyTitle","text":"The text of my question"}
```
Response if the question does not exist:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> GET /questions/non-existing-question HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 404 Not Found
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:18:40 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact
```

### Update a question
Request:
```
curl -v -H "Content-Type: application/json" \
	 -X PUT http://localhost:5000/questions/test \
	 -d '{"text":"Another text"}'
```
Response if the question has been updated:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> PUT /questions/test HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 23
> 
* upload completely sent off: 23 out of 23 bytes
< HTTP/1.1 200 OK
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:19:31 GMT
< Content-Type: application/json
< Content-Length: 53
< 
* Connection #0 to host localhost left intact
{"id":"test","title":"MyTitle","text":"Another text"}
```
Response if the question could not be updated:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> PUT /questions/non-existing-question HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 23
> 
* upload completely sent off: 23 out of 23 bytes
< HTTP/1.1 404 Not Found
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:20:07 GMT
< Content-Type: application/json
< Content-Length: 0
< 
* Connection #0 to host localhost left intact
```

### Delete a question
Request:
```
curl -v -X DELETE http://localhost:5000/questions/test

curl -v -X DELETE http://localhost:5000/customers/sbc123
```
Response:
```
*   Trying ::1...
* Connected to localhost (::1) port 5000 (#0)
> DELETE /questions/test HTTP/1.1
> Host: localhost:5000
> User-Agent: curl/7.43.0
> Accept: */*
> 
< HTTP/1.1 204 No Content
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:20:30 GMT
< Content-Type: application/json
< 
* Connection #0 to host localhost left intact
```


sbt docker
docker run -t -p 5000:5000 com.sbux/akka-http-customer -e "JAVA_OPTS=-Djavax.net.ssl.trustStore=/apps/cassandra.truststore -Djavax.net.ssl.trustStorePassword=Sbux1Sbux1"
docker exec -it [container-id] bash

java -Djavax.net.ssl.trustStore=/apps/cassandra.truststore -Djavax.net.ssl.trustStorePassword=Sbux1Sbux1 -cp /app/akka-slf4j_2.11-2.3.9.jar:/app/config-1.2.1.jar:/app/akka-http-experimental_2.11-2.0.1.jar:/app/akka-http-core-experimental_2.11-2.0.1.jar:/app/akka-parsing-experimental_2.11-2.0.1.jar:/app/akka-stream-experimental_2.11-2.0.1.jar:/app/akka-actor_2.11-2.3.12.jar:/app/ssl-config-akka_2.11-0.1.0.jar:/app/ssl-config-core_2.11-0.1.0.jar:/app/scala-parser-combinators_2.11-1.0.4.jar:/app/reactive-streams.jar:/app/logback-classic-1.1.2.jar:/app/logback-core-1.1.2.jar:/app/json4s-native_2.11-3.2.11.jar:/app/json4s-ext_2.11-3.2.11.jar:/app/joda-time.jar:/app/joda-convert.jar:/app/akka-http-json4s_2.11-1.4.2.jar:/app/json4s-core_2.11-3.3.0.jar:/app/json4s-ast_2.11-3.3.0.jar:/app/json4s-scalap_2.11-3.3.0.jar:/app/paranamer-2.8.jar:/app/scala-xml_2.11-1.0.5.jar:/app/scalactic_2.11-3.0.0.jar:/app/scala-library-2.11.8.jar:/app/scala-reflect-2.11.8.jar:/app/cassandra-driver-core-3.0.1.jar:/app/netty-handler-4.0.33.Final.jar:/app/netty-buffer-4.0.33.Final.jar:/app/netty-common-4.0.33.Final.jar:/app/netty-transport-4.0.33.Final.jar:/app/netty-codec-4.0.33.Final.jar:/app/guava-16.0.1.jar:/app/metrics-core-3.1.2.jar:/app/slf4j-api.jar:/app/akka-http-customer_2.11-1.0.0.jar com.sbux.quiz.mgmt.Main
