<template>
  <div id="card-app">
    <div>
      <div style="text-align:left">
        <h1>Cards. 
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
      error_message: "",
      cards: [],
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
          alert("Can not load cards, " + error)
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
          headers : {
            Authorization: 'SERTIS ' + localStorage.getItem("token")
          }
        })
          .then(res => {
            this.cards = res.data;
            alert('Card deleted !!!!')
            location.reload();
          })
          .catch(error => {
             alert(error)
          });
      }
    },

    addNewCard: function() {
      if (localStorage.getItem("token")) {
          console.log('add new card')
      } else {
          alert('Please login!')
      }
    }
  }
};
</script>