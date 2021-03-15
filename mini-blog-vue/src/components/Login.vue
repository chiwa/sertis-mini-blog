<template>
  <div id="user-app">
    <div v-if="user_login">
      <h3>
        Welcome : {{first_name}} {{last_name}}
        <button v-on:click="logout()">Click to logout</button>
      </h3>
    </div>
    <div v-else>
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
            <button v-on:click="login()">Login</button>
          </td>
        </tr>
      </table>
    </div>


   
  </div>

  
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  props: {
    msg: String
  },

  data() {
    return {
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
    }
  }
};
</script>