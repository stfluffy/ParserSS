# SimbirSoft TestTask

This is a rest api server for parsing web pages. The server stores dictionaries with unique words and url site.


**P.S** For an example of a ready site parsing, you can send a Get request by URL:  http://localhost:8080/api/parser/1 or URL: http://localhost:8080/api/parser/2 

## Installation:

	
**Open a terminal and enter commands:**

	 1. git clone https://github.com/stfluffy/ParserSS.git
	 2. mvn spring-boot:run
## Stack:

 - Java 1.8
 - Spring boot
 - Maven
 - HSQLDB
 - Log4j

## REST API

### Get dictionary by id:

**GET  request:** This query returns a dictionary instance by id:
	
	 |GET| URL: http://localhost8080/api/parser/{id}

- **{id}** -  instance id.

**Error:**

	"error": "notFoundById: {id}". 
	
**Example:** 
> **Try:** Send a |GET| request for url:	http://localhost8080/api/parser/1

>**Result:**

	{
		"result":  {
			"id":  1,
			"url":  "https://www.simbirsoft.com/en",
		    "words":  {
			    "create":  "1",
			    "software":  "4",
			    "for":  "16",
			    "your":  "3",
			    "business":  "3",
			}   
		}	
	}
	
---

### Create dictionary

**POST request:** This request takes request body and returns an instance of the created entity:

	|POST| URL: http://localhost8080/api/parser
	
>**RequestBody:**
>
	{
		"url": "http://{some website}"
	}

> **NOTE:  URL should be only in this format:** 
> - **http:// {some website}**,
> - **https:// {some website}**.


**Errors:**

	"error": "addDictionary: {exception message}"
	"error": "addDictionary.ParserFromHtmlService.getDictionary: {exception message}"
	"error": "addDictionary.ParserFromHtmlService.savePageToFile: {exception message}"
	

**Example:** 
> **Try:** Send a |POST|request for url:	http://localhost8080/api/parser  and

>**RequestBody:**
	
	{
		"url": "https://www.simbirsoft.com/en/"
	}


>**Result:**

	{
		"result":  {
			"id":  1,
			"url":  "https://www.simbirsoft.com/en",
		    "words":  {
			    "create":  "1",
			    "software":  "4",
			    "for":  "16",
			    "your":  "3",
			    "business":  "3",
			}   
		}	
	}

---
### Update dictionary

**PUT request:** This request takes request body and returns an instance of the update entity:

	|POST| URL: http://localhost8080/api/parser/1
	
	
>**RequestBody:**
	
	{
		"words": {
				"modified word":  "modified cout words",
				"modified word":  "modified cout words",
			 }
	}

> **NOTE:  You can only change words in the dictionary**.

**Error:** 

	"error": "notFoundId: {id}"
	

**Example:**  
> **Try:** Send a |PUT|request for url:	http://localhost8080/api/parser/1  and
RequestBody:

	{
		"words": {
				"создаём":  "1",
				"программное":  "1",
				"обеспечение":  "2",
				"для":  "16",
				"вашего":  "1",
				"бизнеса":  "3"
			}
	}


>**Result:**

	{
		"result":  {
			"id":  1,
			"url":  "https://www.simbirsoft.com/en",
		    "words": {
					"создаём":  "1",
					"программное":  "1",
					"обеспечение":  "2",
					"для":  "16",
					"вашего":  "1",
					"бизнеса":  "3"
			}
		}	
	}

---

### Delete dictionary

**DELETE request:** This request deletes the dictionary:

	|DELETE| URL: http://localhost8080/api/parser/{id}

- **{id}** -  instance id.

**Error:**

	"error": "notFoundId: {id}"
	

**Example:** 
> **Try:**  Send a |DELETE|request for url:	http://localhost8080/api/parser/1 


>**Result:** HTTP status 200


---

