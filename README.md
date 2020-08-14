# SimbirSoft TestTask

This is a rest api server for parsing web pages. The server stores dictionaries with unique words and site url.


**P.S** For an example of a site parsing, you can send Get request by URL:  http://localhost:8080/api/parser/1w 

## Installation:

	
**Open terminal and enter commands:**

	 1. git clone https://github.com/stfluffy/ParserSS.git
	 2. mvn spring-boot:run
## Stack:

 - Java 1.8
 - Spring boot
 - Maven
 - HSQLDB

## REST API

### Get dictionary by id:

**GET  request:** This query returns dictionary instance by id:
	
	 |GET| URL: http://localhost8080/api/parser/{id}

- **{id}** -  instance id.


	
**Example:** 
> **Try:** Send |GET| request for url:	http://localhost8080/api/parser/1

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

> **NOTE:  URL should only be this format:** 
> - **http:// {some website}**,
> - **https:// {some website}**.	

**Example:** 
> **Try:** Send |POST|request for url: http://localhost8080/api/parser and

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

**POST request:** This request delete old instance and return updated:

	|POST| URL: http://localhost8080/api/parser/1
	

**Example:**  
> **Try:** Send |POST| request for url: http://localhost8080/api/parser/1 


>**Result:**

	{
    		"result":  {
    			"id":  2,
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


**Example:** 
> **Try:**  Send |DELETE| request for url: http://localhost8080/api/parser/1 


>**Result:** HTTP status 200


---

