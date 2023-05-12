# Search engine
This project is an attempt to create a search engine for educational purposes, involving various backend and general concepts and tools like logging, testing, threads, security and databases.

## Requirements
Made with spring boot, jdk 17, maven, postgreSQL. 

## API
The REST API is described below

### Indexing
This will index an url (root) and the url referred by the content of the site recursively to the depth specified in the call.
#### Request
```
POST /api/v1
```

```
curl -v -X POST -F url=https://es.wikipedia.org/wiki/Identidad_corporativa -F depth=1 http://localhost:8080/api/v1/
```

#### Response
### <p style="background: green; width: fit-content; margin: 10px; padding: 3px 5px"> 200: </p>

```
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 12 May 2023 18:37:06 GMT

{"status":"OK","message":"indexado"}
```

