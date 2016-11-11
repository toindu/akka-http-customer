git cline https://github.com/toindu/akka-http-customer

# Quiz Management Service
Tutorial on how to build a REST CRUD application with Akka-Http

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
     	 -X POST http://localhost:5000/customers \
     	 -d '{"id": "sbc123", "name": "Indrajit Banerjee", "mobile":123456}'

```
Response if the question has been created:
```
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

curl -v http://localhost:5000/customers/test
curl -v http://localhost:5000/customers/sbc123
```
Response if the question exists:
```
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
	 -X PUT http://localhost:5000/customers/test \
	 -d '{"name":"Another text"}'
```
Response if the question has been updated:
```
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

curl -v -X DELETE http://localhost:5000/customers/sbc123
```
Response:

< HTTP/1.1 204 No Content
< Server: akka-http/2.3.12
< Date: Sun, 07 Feb 2016 11:20:30 GMT
< Content-Type: application/json
< 



sbt docker
docker run -t -p 5000:5000 com.sbux/akka-http-customer
docker exec -it [container-id] bash
