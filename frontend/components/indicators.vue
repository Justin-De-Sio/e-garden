<template>
    <div class="custom-frame">
      <div class="icon-background" :style="{ backgroundColor: iconBackgroundColor }">
        <img :src="iconPath" alt="icon" class="icon-image" />
      </div>
      <div class="text-container">
        <h1>{{ title }} </h1>
        <h2>{{ subtitle }}</h2>
      </div>
    </div>
</template>
  
<script setup>
  import { defineProps } from 'vue';
  
  defineProps({
    iconPath: {
      type: String ,
      required: true,
    },
    iconBackgroundColor: {
      type: String,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    subtitle: {
      type: String,
      required: true,
    },
  });
</script>
  
  
  <style scoped>
  
  
  .custom-frame {
    width: 100% ;
    max-width: 18rem;
    min-width: 10.5rem;
    height: 100%;
    background-color: #FFFFFF; 
    display: flex;
    flex-direction: row; 
    align-items: center;
    padding: clamp(0.6rem, 1.5vw, 1rem);
    font-family: 'gilroy-medium', sans-serif; 
    border-radius: 0.8rem; 
  }
  .icon-background {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 15px; 
    width: 20%;
    height: auto;
    padding: 0.09rem;
    border-radius: 0.5rem; 
  }
  .icon-image {
    width: 100%; 
    height: auto;
    padding: clamp(0.3rem, 0.7vw, 3rem);

  }
  .text-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
  .custom-frame h1 {
    font-size: clamp(0.7rem, 0.8vw, 1rem); 
    margin: 2px;
    color: #000000;
  }
  .custom-frame h2 {
    font-size: clamp(0.6rem, 0.8vw, 0.9rem); 
    color: #7B7B7B;
    margin: 0;
  }

  @media screen and (max-width: 800px) {

    .custom-frame{
      padding: 0.5rem  
    }

    .icon-image {
    width: 100%; 
    height: auto;
    padding: 0.6rem;

  }
  }
  @media screen and (max-width: 400px) {

  .custom-frame{
    padding: 0.5rem  
  }

  .icon-image {
  width: 100%; 
  height: auto;
  padding: 0.3rem;

  }
    
}
  
  </style>
  