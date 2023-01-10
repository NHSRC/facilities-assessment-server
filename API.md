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
    * number = Current page's number (zero based). When you call it for the first time this will be equal to 0.
  * Wherever applicable API can be invoked by using a lastModifiedDate parameter. When this parameter is passed the results will contain resources which have been changed after this time.
* The date parameter should be passed in ISO format like 1900-01-01 00:00:00.001Z
* All entities are identified a UUID in string format like 4dc48f20-da72-43d4-b5db-ba7a0ddc342a
* The format for each API is as follows:
  * The on-wire format
  * Explanation of the API
* If you use Postman app for managing testing web apis, then you can use this https://www.getpostman.com/collections/d3c85d05ea0624ca1dca to get started for Gunak. Please note that, you would need to define your environment variables in Postman. Postman could also help you with generating code in many languages for using the API.

## API
### Register user
#### Format
```
POST /registration HTTP/1.1
Host: SERVER:PORT
Content-Type: application/json
Cache-Control: no-cache

{
	"firstName": "FIRST_NAME",
	"lastName": "LAST_NAME",
	"email": "EMAIL",
	"password": "PASSWORD",
	"userType": "USER_TYPE",
	"userTypeName": "USER_TYPE_NAME"
}
```
#### Description
* User type should be one of these values - State, AssessmentToolMode. State implies access to the state level data and Program implies access to program (NQAS, Kayakalp etc) level data.
* User type name is the name for chosen user type. For State it should be the name of the state and in case of AssessmentToolMode it should be program name.
* Note that this creates a user inactive mode. You need to contact the system administartor to enable the user to be able to login.

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
When the post is successful in your response you would get cookie by name GUNSESSID. This indicates a successful session being established.

### Get all assessment types
#### Format
```
GET /api/assessmentType HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```
#### Description
This API provides a complete list of Assessment Types. Currently there are three types: Internal, External and Peer. This is required when submitting the assessment.

### Get all assessment tools
#### Format
```
GET /api/assessmentTool HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```
#### Description
This API provides a complete list of Assessment Tools.

### Get checklists for assessment tool
#### Format
```
GET /api/checklist/search/forAssessmentTool?assessmentToolUuid=UUID&amp;lastModifiedDate=DATETIME&amp;size=SIZE&amp;page=PAGE HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```

### Get area of concern for checklist
#### Format
```
GET /api/areaOfConcern/search/forChecklist?checklistUuid=UUID&amp;lastModifiedDate=DATETIME&amp;size=SIZE&amp;page=PAGE HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```

### Get standard for area of concern
#### Format
```
GET /api/standard/search/forAreaOfConcern?areaOfConcernUuid=UUID&amp;lastModifiedDate=DATETIME&amp;size=SIZE&amp;page=PAGE HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```

### Get measurable element for standard
#### Format
```
GET /api/measurableElement/search/forStandard?standardUuid=UUID&amp;lastModifiedDate=DATETIME&amp;size=SIZE&amp;page=PAGE HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```

### Get checkpoint for measurableElement
#### Format
```
GET /api/checkpoint/search/forMeasurableElement?measurableElementUuid=MEUUID&amp;checklistUuid=CLUUID&amp;lastModifiedDate=DATETIME&amp;size=SIZE&amp;page=PAGE HTTP/1.1
Host: SERVER:PORT
Cache-Control: no-cache
```
#### Description
Note that in this call you also need to pass the checklist uuid along with measurable element uuid.

### Create or Update Facility Assessment
#### Format
```
POST /api/facility-assessment HTTP/1.1
Host: SERVER:PORT
Content-Type: application/json
Cache-Control: no-cache

{
	"uuid": "UUID_OF_FACILITY_ASSESSMENT",
	"facility": "FACILITY_UUID",
	"facilityName": "FACILITY_NAME",
	"assessmentTool": "ASSESSMENT_TOOL_UUID",
	"startDate": "START_DATE_OF_ASSESSMENT",
	"endDate": "END_DATE_OF_ASSESSMENT",
	"seriesName": "ASSESSMENT_SERIES_NAME",
	"assessmentTypeUUID": "ASSESSMENT_TYPE_UUID"
}
```
#### Description
* UUID_OF_FACILITY_ASSESSMENT should be the new uuid to be provided by the caller
* Provide either the facility or facilityName. The facility name should be used only if the facility is not found in the Gunak database.
* Assessment Type UUID can be found from the response of the API for getting the Assessment Type (descibed above).
* Each facility goes through multiple assessments of same type. Hence to differentiate between them, a series name should be used. This has to be unique for that type of assessment.

### Create or Update Checkpoint Scores
#### Format
```
POST /api/facility-assessment/checklist HTTP/1.1
Host: SERVER:PORT
Content-Type: application/json
Cache-Control: no-cache

{
	"uuid": "CHECKLIST_UUID",
	"facilityAssessment": "FACILITY_ASSESSMENT_UUID",
	"checkpointScores" : [
		{
			"uuid": "UUID_OF_CHECKPOINT_SCORE",
			"uuid": "CHECKPOINT_UUID",
			"score": SCORE,
			"remarks": "REMARKS",
			"na": NA
		},...
	]
}
```
#### Description
* CHECKLIST_UUID and UUID_OF_CHECKPOINT_SCORE(s) should be the new uuid to be provided by the caller
* SCORE is either 0, 1 or 2
* REMARKS if any provided by the assessor when filling the score
* NA is the boolean value of true or false (default is false). It is true when the user has marked this checkpoint as not applicable instead of providing a score.
* Note that checkpoint score is an array.


## API Cookbook
The above explains the individual APIs. This part of documentation deals with dealing with specific use cases.
### Save Assessment and Scores
For saving Facility Assessment and the scores corresponding to that assessment you need two narrow down on the key resource UUIDs. Those are:
* Facility
* Assessment Tool
* Assessment Type
* Checkpoints (with which the scores are associated)
IN PROGRESS......
