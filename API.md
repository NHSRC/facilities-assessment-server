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
There are multipe checklists within an Assessment Tool. Some examples of these checklists are:
* Labour Room
* Blood Bank
* Accident & Emergency
* Kayakalp

## Pre-requisites for using the API
For using the API of Gunak you need to be registered as an active user in the system. Please contact the system administrator for the login (EMAIL) and PASSWORD. You also need ask for the SERVER location.

## About the API
* All Gunak API works over https. The unsecured resources also work over http.
* The format for each API is as follows:
  * The on-wire format
  * Explanation of the API

## API
### Login
#### Format
```
POST /login HTTP/1.1
Host: SERVER:443
Content-Type: application/x-www-form-urlencoded
Cache-Control: no-cache

email=EMAIL&password=PASSWORD
```
#### Description
When the post is successful in your response you would get cookie by name JSESSIONID. This indicates a successful session being established.

