<template>
  <div id="card-app">
    <div>
      <div style="text-align:left">
        <h1>
          Cards.
          <img
            class="menu_links"
            v-on:click="addNewCard()"
            style="margin:5px"
            width="20"
            height="20"
            src="../assets/add.png"
          />
        </h1>
      </div>

      <div class="floatLeft2" style="text-align:left" v-if="show_add_card">
        <h3>
          &nbsp;Add new card.
          <img
            class="menu_links"
            v-on:click="show_add_card=false"
            style="margin:5px"
            width="120"
            height="40"
            src="../assets/cancel.jpeg"
          />
        </h3>
        <table>
          <tr>
            <td>
              <b>Category :</b>
            </td>
            <td>
              <select v-model="categoryId">
                <option
                  :value="category.id"
                  v-for="category in categories"
                  :key="category.id"
                >{{category.category_name}}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <b>Topic :</b>
            </td>
            <td>
              <input v-model="topic" type="text" id="topic" />
            </td>
          </tr>
          <tr>
            <td>
              <b>Content :</b>
            </td>
            <td>
              <textarea v-model="content" rows="5" cols="40">xxxxxxx</textarea>
            </td>
          </tr>
          <tr>
            <td>
              <button v-on:click="addNewCardToDb()">Add</button>
            </td>
            <td></td>
          </tr>
        </table>
      </div>

      <div v-for="card in cards" :key="card.id">
        <div class="floatLeft">
          <h3>
            {{card.topic}} [{{card.category.category_name}}]
            <br />
            <img
              class="menu_links"
              v-on:click="deleteCard(card.id)"
              v-if="checkIfOwner(card.user.username)"
              style="margin:5px"
              width="20"
              height="20"
              src="../assets/delete.jpeg"
            />
            <img
              class="menu_links"
              v-on:click="deleteCard(card.id)"
              v-if="checkIfOwner(card.user.username)"
              style="margin:5px"
              width="20"
              height="20"
              src="../assets/edit.jpeg"
            />
          </h3>
          Owner : {{card.user.first_name}} {{card.user.last_name}}
          <br />
          Last Mofied : {{card.last_modified}}
          <hr />
          {{card.content}}
        </div>
      </div>
    </div>
  </div>
</template>
 
<style scoped>
.menu_links {
  cursor: pointer;
}
.floatLeft {
  float: left;
  width: 340px;
  height: 340px;
  margin: 10px;
  background-color: #a4c739;
  text-align: center;
  line-height: 30px;
}
.floatLeft2 {
  float: left;
  width: 450px;
  height: 400px;
  margin: 10px;
  background-color: #a8d358;
  text-align: center;
  line-height: 30px;
}
</style>

<script>
import axios from "axios";

export default {
  name: "Card",
  props: {
    msg: String
  },

  data() {
    return {
      categoryId: 1,
      topic: "",
      content: "",
      show_add_card: false,
      error_message: "",
      cards: [],
      categories: [],
      mini_blog_api_url: "http://localhost:8080"
    };
  },
  created: function() {
    this.listAllCards();
  },
  methods: {
    listAllCards: function() {
      axios({
        method: "GET",
        url: this.mini_blog_api_url + "/cards"
      })
        .then(res => {
          this.cards = res.data;
        })
        .catch(error => {
          alert("Can not load cards, " + error);
        });
    },

    listAllCategories: function() {
      axios({
        method: "GET",
        url: this.mini_blog_api_url + "/categories",
        headers: {
          Authorization: "SERTIS " + localStorage.getItem("token")
        }
      })
        .then(res => {
          this.categories = res.data;
        })
        .catch(error => {
          alert("Can not load cards, " + error);
        });
    },

    checkIfOwner: function(userName) {
      if (localStorage.getItem("username")) {
        if (userName === localStorage.getItem("username")) {
          return true;
        }
      }
      return false;
    },

    deleteCard: function(cardId) {
      if (confirm("Delete this card ?")) {
        axios({
          method: "DELETE",
          url: this.mini_blog_api_url + "/cards/" + cardId,
          headers: {
            Authorization: "SERTIS " + localStorage.getItem("token")
          }
        })
          .then(res => {
            this.cards = res.data;
            alert("Card deleted !!!!");
            location.reload();
          })
          .catch(error => {
            alert(error);
          });
      }
    },

    addNewCard: function() {
      if (localStorage.getItem("token")) {
        console.log("add new card");
        this.show_add_card = true;
        this.listAllCategories();
      } else {
        alert("Please login!");
      }
    },

    addNewCardToDb: function() {
      axios({
        method: "POST",
        url: this.mini_blog_api_url + "/cards",
        headers: {
          Authorization: "SERTIS " + localStorage.getItem("token")
        },
        data: {
          topic: this.topic,
          content: this.content,
          category_id: this.categoryId
        }
      })
        .then(res => {
          console.log(res);
          alert("Added card successfuly.")
          location.reload();
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data.error_message); // => the response payload
            this.error_message = error.response.data.error_message;
          } else {
            this.error_message = error;
          }
          alert(this.error_message);
        });
    }
  }
};
</script>