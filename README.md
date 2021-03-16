# Mini Blog Assignment for Chiwa Kantawong

This demo application is build by spring-boot and using in-memory DB(H2) that provide the Mini Card API.
 
*** Please concern that we are using in-memory Database so if you stop the application then
the Data will be lose (It's for development environment)

## How to run the API application?

Please checkout the project from https://github.com/chiwa/sertis-mini-blog.git

~~~
git clone https://github.com/chiwa/sertis-mini-card.git
cd sertis-mini-blog/mini-blog-api
mvn clean install
mvn spring-boot:run

~~~


** If mvn clean install , The Unit Test Failed pleas ignore test

~~~
 mvn clean install -DskipTests
~~~

When the application starting some dummy users are created you can access all APIs as below

### Login API

~~~
curl --insecure -X POST \
  http://localhost:8080/login \
  -H 'content-type: application/json' \
  -d '{
	"username":"chiwa",
	"password":"password"
}'
~~~

response : 

~~~
{ 
    "user_name":"chiwa",
    "first_name":"Chiwa",
    "last_name":"Kantawong",
    "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjE5MzYzLCJleHAiOjE2MTU2MzczNjN9.fGOTxwx1eRTpdr4Lx-waDMRCFMaH2g7NMJg4eehl8iE"
}
~~~

The system will return you the token for using in the other secure API.

###Login with incorrect user or password

~~~
curl --insecure -X POST \
  http://localhost:8080/login \
  -H 'content-type: application/json' \
  -d '{
        "username":"chiwax",
        "password":"password"
}'
~~~

Response :

~~~
{"status_code":401,"error_message":"User chiwax not found.","developer_message":null}
~~~


### Get User
Get the logged in user information

~~~
curl --insecure -X GET \
  http://localhost:8080/users \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjE5MzYzLCJleHAiOjE2MTU2MzczNjN9.fGOTxwx1eRTpdr4Lx-waDMRCFMaH2g7NMJg4eehl8iE'
~~~

response :

~~~
{ 
    "user_name":"chiwa",
    "first_name":"Chiwa",
    "last_name":"Kantawong",
    "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjE5MzYzLCJleHAiOjE2MTU2MzczNjN9.fGOTxwx1eRTpdr4Lx-waDMRCFMaH2g7NMJg4eehl8iE"
}
~~~

## Create User
Create new user

~~~
curl --insecure -X POST \
  http://localhost:8080/register-users \
  -H 'content-type: application/json' \
  -d '{
	"username":"test",
	"password":"test",
	"first_name" : "test",
	"last_name" : "test"
}'
~~~

Response :

~~~
{
    "username":"test",
    "first_name":"test",
    "last_name":"test"
}
~~~

## Delete User
Delete logged in user

~~~
curl --insecure -X DELETE \
  http://localhost:8080/users \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjQ2NjQ0LCJleHAiOjE2MTU2NjQ2NDR9.rM0Z4FmX9eWTHKQ2JxzhkGxWPCENvmme4xg-XCIOP8g'
~~~

Response : 200

### Get All Categories
Get all Categories

~~~
curl --insecure -X GET \
  http://localhost:8080/categories \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjUwMTA4LCJleHAiOjE2MTU2NjgxMDh9.hsRKjylaBQImpeafv31bJasxuy2H7-h-B9xxNZ2Mb2Y'
~~~

response :

~~~
[{"category_name":"Travel"},{"category_name":"Programming"},{"category_name":"Foods"}]%
~~~

### Get Category by id
Get Category by id

~~~
curl --insecure -X GET \
  http://localhost:8080/categories/1 \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjUwMTA4LCJleHAiOjE2MTU2NjgxMDh9.hsRKjylaBQImpeafv31bJasxuy2H7-h-B9xxNZ2Mb2Y'
~~~

response :

~~~
{"id":1,"category_name":"Travel"}
~~~


## Create new Card
Create new Card

~~~
curl --insecure -X POST \
  http://localhost:8080/cards \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc' \
  -H 'content-type: application/json' \
  -d '{
	"topic":"test",
	"content":"test",
    "category_id" : 1
}'
~~~

Response :

~~~
{"id":6,"user":{"username":"chiwa","first_name":"Chiwa","last_name":"Kantawong"},"category":{"id":1,"category_name":"Travel"},"topic":"test","content":"test","last_modified":"14/03/2021"}
~~~

### Get All Cards
Get all Cards

~~~
curl --insecure -X GET \
http://localhost:8080/cards \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc'
~~~

response :
~~~
[{"id":1,"user":{"username":"chiwa","first_name":"Chiwa","last_name":"Kantawong"},"category":{"id":2,"category_name":"Programming"},"topic":"Install JDK","content":"This is the guide......","last_modified":"13/03/2011"},{"id":2,"user":{"username":"chiwa","first_name":"Chiwa","last_name":"Kantawong"},"category":{"id":2,"category_name":"Programming"},"topic":"Hello World Java","content":"Create new application......","last_modified":"13/03/2011"},{"id":3,"user":{"username":"jirapa","first_name":"Jirapa","last_name":"Kantawong"},"category":{"id":1,"category_name":"Travel"},"topic":"Travel to Japan","content":"Tokyo......","last_modified":"13/03/2011"},{"id":4,"user":{"username":"sura","first_name":"Sura","last_name":"Jiranan"},"category":{"id":3,"category_name":"Foods"},"topic":"Pizza","content":"How to cook pizza......","last_modified":"13/03/2011"},{"id":5,"user":{"username":"chiwa","first_name":"Chiwa","last_name":"Kantawong"},"category":{"id":1,"category_name":"Travel"},"topic":"test","content":"test","last_modified":"13/03/2021"}]
~~~

### Get Card by id
Get Card by id

~~~
curl --insecure -X GET \
  http://localhost:8080/cards/1 \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc'
~~~

response :
~~~
{"id":1,"user":{"username":"chiwa","first_name":"Chiwa","last_name":"Kantawong"},"category":{"id":2,"category_name":"Programming"},"topic":"Install JDK","content":"This is the guide......","last_modified":"13/03/2011"}
~~~

## Update Card
Update Card

~~~
curl --insecure -X PUT \
http://localhost:8080/cards/1 \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc' \
  -H 'content-type: application/json' \
  -d '{
        "topic":"test edit",
        "content":"test edit",
    "category_id" : 1
}'
~~~

Response :

~~~
{"id":1,"user":{"username":"chiwa","first_name":"Chiwa","last_name":"Kantawong"},"category":{"id":1,"category_name":"Travel"},"topic":"test edit","content":"test edit","last_modified":"14/03/2021"}
~~~

## Update other user card
Update other user card

~~~
curl --insecure -X PUT \
http://localhost:8080/cards/3 \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc' \
  -H 'content-type: application/json' \
  -d '{
        "topic":"test edit",
        "content":"test edit",
    "category_id" : 1
}'
~~~

Response :
~~~
{"status_code":500,"error_message":"You are not the owner of this card","developer_message":"You are not the owner of this card"}
~~~

## DELETE Card
Delete Card

~~~
curl --insecure -X DELETE \
http://localhost:8080/cards/1 \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc' \
  -H 'content-type: application/json'
~~~

Response :

~~~
true
~~~

## DELETE other user's Card
Delete other user's Card

~~~
curl --insecure -X DELETE \
http://localhost:8080/cards/3 \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjkxMzk1LCJleHAiOjE2MTU3MDkzOTV9.DUneh1Q3o_TvDKlBp2UbjBHEO1cMAK-BRJrDUZtQLoc' \
  -H 'content-type: application/json'
~~~

Response :

~~~
{"status_code":500,"error_message":"You are not the owner of this card","developer_message":"You are not the owner of this card"}
~~~

## How to run the Vue application?


## Project setup


```
brew update

brew install node

npm install

npm install -g @vue/cli
```

You need to install Axios on you machine follow by this https://vuejs.org/v2/cookbook/using-axios-to-consume-apis.html

## Compiles and hot-reloads for development
```
npm run serve -- --port 3000
```

When the application start you can acdess to http://localhost:3000

<img src="images/s1.png"/>

### Register User 

Click on Add User button

<img src="images/s2.png"/>

### Login

If you already have the user, you can login

<img src="images/s3.png"/>

If login success you will see the welcome user session & you will see the edit/delete button on your own card

<img src="images/s4.png"/>

### Create new Card

You can click on + button

<img src="images/s5.png"/>

you will see the Add new card session

<img src="images/s6.png"/>

### Delete Card

Click on delete button

<img src="images/s7.png"/>

### Update Card

Click on update button

<img src="images/s7.png"/>

Then you will see the edit session  

<img src="images/s8.png"/>






