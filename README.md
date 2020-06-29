# SimbirSoft TestTask

**About:** This is a rest api server for parsing web pages. The server stores dictionaries with unique words and links to the site.

## Stack:

 - Java 1.8
 - Spring boot
 - Maven
 - HSQLDB
 - Log4j

## REST API

### Find dictionary by id

**GET  request:** This query returns a dictionary instance by id:
	
	 |GET| URL: http://localhost8080/api/parser/{id}

- **{id}** -  instance id.

**ERROR:**

	"error": "notFoundById: {id}". 
	
**EXAMPLE:** 
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
	
	RequestBody:
				{
					"url": "http://{some website}"
				}

> **NOTE:  URL should be only in this format:** 
> **- http:// {some website}**,
> **- https:// {some website}**.


**ERROR:**

	"error": "addDictionary: {exception message}"
	"error": "addDictionary.ParserFromHtmlService.getDictionary: {exception message}"
	"error": "addDictionary.ParserFromHtmlService.savePageToFile: {exception message}"
	

**EXAMPLE:** 
> **Try:** Send a |POST|request for url:	http://localhost8080/api/parser  and

	RequestBody:
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

### Delete dictionary

**DELETE request:** This request deletes the dictionary:

	|DELETE| URL: http://localhost8080/api/parser/{id}

- **{id}** -  instance id.

**ERROR:**

	"error": "notFoundId: {id}"
	

**EXAMPLE:** 
> **Try:**  Send a |DELETE|request for url:	http://localhost8080/api/parser/1 


>**Result:** HTTP status 204


---

