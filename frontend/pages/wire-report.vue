<template>
  <div class="wrapper_page">
    <navbar-vertical/>
    <div class="wrapperLeftRight">
      <div class="header_bis">
        <header_title title="Page d'accueil" subtitle="Retrouvez tous les rapports pour la gestion de votre potager"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import navbarVertical from "~/components/navbar_vertical.vue"
import header_title from "~/components/header_title.vue";


definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR", "RESPONSABLE"],
})
</script>

<style scoped>

.wrapper_page{
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  max-width: 100vw;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.wrapperLeftRight{
  display: flex;
  flex-grow: 1;
}

.left_content{
  display: flex;
  flex-direction: column;
  width: 60%;
  flex-grow: 1;
  padding: 0 2rem;
}
.header_bis{
  width: 80%;
  height: auto;
  padding: 2rem 0;
}


.columns_indicators{
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

.top_wrapper{

  width: 100%;
  height: 100%;
}
.bot_left{
  width: 100%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  margin: 3rem 0;
  padding-right: 3rem;
  gap: 5rem;
}

.top_bot_left{
  width: 100%;
  height: auto;
}

.top_bot_left h2{
  font-family: "Gilroy-Medium";
  font-size: clamp(1rem, 2vw, 1.5rem);
  margin-bottom: 2rem;

}

.top_bot_right h2{
  font-family: "Gilroy-Medium";
  font-size: clamp(1rem, 2vw, 1.5rem);
  margin-bottom: 2rem;

}

.right_calendar{
  margin-top: 5rem;
  margin-right: 4rem;
}

footer{
  font-family: "Gilroy-Medium";
  text-align: center;
  padding: 1rem 0;
  width: 100%;
}


@media screen and (max-width: 800px){
  .wrapper_content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    padding: 0 1rem;
  }

  .bot_left{
    width: 100%;
  }

  .header_bis{
    text-align: center;
  }

}


h1 {
  font-family: "Gilroy-Medium", sans-serif;
  color: black;
  font-size: 2rem;
}

h2 {
  font-family: "Gilroy-Medium", sans-serif;
  margin-bottom: 2vh;
}

h3 {
  font-family: "Gilroy-Regular", sans-serif;
  color: #969696;
  font-size: 1rem;
}




</style>