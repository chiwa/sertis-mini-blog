<template>
  <div id="user-app">
    <div v-if="register_new_user" class="floatLeft3">
      <h3>Register new user.</h3>
      <table border="0">
        <tr>
          <td>User Name :</td>
          <td>
            <input type="text" id="username" v-model="username" />
          </td>
        </tr>
        <tr>
          <td>Password :</td>
          <td>
            <input type="password" id="password" v-model="password" />
          </td>
        </tr>
        <tr>
          <td>First Name :</td>
          <td>
            <input type="text" id="first_name" v-model="first_name" />
          </td>
        </tr>
        <tr>
          <td>Last Name :</td>
          <td>
            <input type="text" id="first_name" v-model="last_name" />
          </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>
            <button v-on:click="createNewUser()">Create</button>&nbsp;
            <button v-on:click="register_new_user=false">Cancel</button>
          </td>
        </tr>
      </table>
    </div>

    <div v-if="user_login">
      <h3>
        Welcome : {{first_name}} {{last_name}}
        <button v-on:click="logout()">Click to logout</button>
      </h3>
    </div>
    <div v-else>
      <div v-if="!register_new_user" class="floatLeft4">
        <table border="0">
          <tr>
            <td></td>
            <td>
              <font color="red">{{error_message}}</font>
            </td>
          </tr>
          <tr>
            <td>User Name :</td>
            <td>
              <input type="text" id="username" v-model="username" />
            </td>
          </tr>
          <tr>
            <td>Password :</td>
            <td>
              <input type="password" id="password" v-model="password" />
            </td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>
              <button v-on:click="login()">Login</button>&nbsp;
              <button v-on:click="register_new_user=true">New User</button>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.floatLeft3 {
  float: center;
  width: 400px;
  height: 250px;
  margin: 10px;
  background-color: #f3f7c3;
  text-align: center;
  line-height: 30px;
}
.floatLeft4 {
  width: 700px;
  height: 150px;
  margin: 10px;
  background-color: #dff57f;
  text-align: center;
  line-height: 30px;
}
</style>

<script>
import axios from "axios";

export default {
  name: "Login",
  props: {
    msg: String
  },

  data() {
    return {
      register_new_user: false,
      user_login: false,
      username: "",
      password: "",
      first_name: "",
      last_name: "",
      token: "",
      error_message: "",
      mini_blog_api_url: "http://localhost:8080"
    };
  },
  created: function() {
    this.user_login = localStorage.getItem("isLogin") == "true";
    this.token = localStorage.getItem("token");
    this.first_name = localStorage.getItem("firstName");
    this.last_name = localStorage.getItem("lastName");
  },
  methods: {
    login: function() {
      localStorage.setItem("isLogin", true);
      this.user_login = true;
      axios({
        method: "POST",
        url: this.mini_blog_api_url + "/login",
        data: {
          username: this.username,
          password: this.password
        }
      })
        .then(res => {
          localStorage.setItem("token", res.data.token);
          localStorage.setItem("userId", res.data.id);
          localStorage.setItem("username", res.data.user_name);
          localStorage.setItem("firstName", res.data.first_name);
          localStorage.setItem("lastName", res.data.last_name);
          this.token = res.data.token;
          this.first_name = res.data.first_name;
          this.last_name = res.data.last_name;
          this.error_message = "";
          location.reload();
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data.error_message); // => the response payload
            this.error_message = error.response.data.error_message;
          } else {
            this.error_message = error;
          }
          this.clearUserData();
        });
    },

    logout: function() {
      localStorage.setItem("isLogin", false);
      this.user_login = false;
      this.clearUserData();
      location.reload();
    },

    clearUserData: function() {
      localStorage.setItem("isLogin", false);
      this.user_login = false;
      this.username = "";
      this.password = "";
      localStorage.removeItem("token");
      localStorage.removeItem("userId");
      localStorage.removeItem("username");
      localStorage.removeItem("firstName");
      localStorage.removeItem("lastName");
    },

    createNewUser: function() {
      axios({
        method: "POST",
        url: this.mini_blog_api_url + "/register-users",
        data: {
          username: this.username,
          password: this.password,
          first_name: this.first_name,
          last_name: this.last_name
        }
      })
        .then(res => {
          console.log(res);
          alert("Register user successfuly.");
          location.reload();
        })
        .catch(error => {
          if (error.response) {
            alert(error.response.data.error_message);
          } else {
            alert(error);
          }
        });
    }

  }
};
</script>