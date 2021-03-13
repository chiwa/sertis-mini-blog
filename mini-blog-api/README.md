# Mini Blog Assignment for Chiwa Kantawong

This demo application is build by spring-boot and using in-memory DB(H2)  that provide the Mini Blog API.

### Setting up Lombok with Eclipse and Intellij

- https://www.baeldung.com/lombok-ide

## How to run the application?

Please checkout the project from https://github.com/chiwa/sertis-mini-blog.git

~~~
git clone https://github.com/chiwa/sertis-mini-blog.git
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
Get the logged in user information

~~~
curl --insecure -X GET \
  http://localhost:8080/categories \
  -H 'authorization: SERTIS eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaGl3YSIsImlzcyI6Imh0dHA6Ly93d3cuc2VydGlzLmNvLnRoIiwiaWF0IjoxNjE1NjUwMTA4LCJleHAiOjE2MTU2NjgxMDh9.hsRKjylaBQImpeafv31bJasxuy2H7-h-B9xxNZ2Mb2Y'
~~~

response :

~~~
[{"category_name":"Travel"},{"category_name":"Programming"},{"category_name":"Foods"}]%
~~~
