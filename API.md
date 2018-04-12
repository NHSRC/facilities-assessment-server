## About the documentation
All the keywords which have been mentioned in CAPS are data element which you would need to replace.

## Data Model
### Assessment Tool
In each assessment the assessors uses an assessment tool. This tool consists of one or more checklists. Examples of assessment tool are: District Hospital (DH), District Hospital (DH), Primary Health Center (PHC) etc. Assessment tool can be of different types (called Assesssment Tool Mode).

### Assessment Tool Mode
This is of the following types as of now.
* nqas
* Kayakalp
* LAQSHYA

### Checklist
There are multiple checklists within an Assessment Tool. Some examples of these checklists are:
* Labour Room
* Blood Bank
* Accident & Emergency
* Kayakalp

### Area of Concern, Standard, Measurable Element and Checkpoint
* Area of concerns are mapped to checklists
* Each area of concern consists of multiple Standards
* Each standard consists of multiple Measurable Element
* Each Measurable Element consists of multiple Checkpoints

## Pre-requisites for using the API
For using the API of Gunak you need to be registered as an active user in the system. Please contact the system administrator for the login (EMAIL) and PASSWORD. You also need ask for the SERVER name and PORT number.

## About the API
* All Gunak API works over https. The unsecured resources also work over http.
* The response type for the API call is JSON.
* The response is paginated when the number of resources returned are more than 1000.
 * The response page size can also be controlled by query paramerter - "size".
 * When the response is paginated then the pagination details are included in the page.
  * size = maximum number of elements in a page
  * totalElements = actual number of elements in current page
  * totalPages = Total number of pages
  * number = Current page's number (zero based)
 * 
* The date parameter should be passed in ISO format
* All entities are identified a UUID in string format
* The format for each API is as follows:
  * The on-wire format
  * Explanation of the API

## API
### Login
#### Format
```
POST /login HTTP/1.1
Host: SERVER:PORT
Content-Type: application/x-www-form-urlencoded
Cache-Control: no-cache

email=EMAIL&password=PASSWORD
```
#### Description
When the post is successful in your response you would get cookie by name JSESSIONID. This indicates a successful session being established.

### Get all assessment tools
#### Format
```
GET /api/assessmentTool HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```
#### Description
This API provides a complete list of Assessment Tools.

### Get checklists for your assessment tool
#### Format
```
GET /api/checklist/search/forAssessmentTool?assessmentToolUuid=4ccad794-b011-4dda-8157-0083d23a7b89&amp;lastModifiedDate=1900-01-01T00:00:00.001Z&amp;size=200&amp;page=0 HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```
#### Description
