<template>
    <div class="custom-frame">
      <div class="icon-background" :style="{ backgroundColor: iconBackgroundColor }">
        <img :src="iconPath" alt="icon" class="icon-image" />
      </div>
      <div class="text-container">
        <h1>{{ title }}</h1>
        <h2>{{ subtitle }}</h2>
      </div>
    </div>
</template>
  
<script setup>
  import { defineProps } from 'vue';
  
  defineProps({
    iconPath: {
      type: String,
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
  @font-face {
    font-family: 'gilroy-medium';
    src: url('@/assets/fonts/Gilroy-Medium.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
  }
  
  .custom-frame {
    width: 256px;
    height: 81px;
    background-color: #FFFFFF; 
    display: flex;
    flex-direction: row; 
    align-items: center;
    padding: 0 10px; 
    font-family: 'gilroy-medium', sans-serif; 
    border-radius: 15px; 
  }
  .icon-background {
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px; 
    margin-left: 12px;
    width: 38px;
    height: 38px;
  }
  .icon-image {
    width: 21px; 
    height: 21px; 
  }
  .custom-frame i {
    font-size: 21px; 
  }
  .text-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
  .custom-frame h1 {
    font-size: 13px;
    margin: 0;
    color: #000000;
  }
  .custom-frame h2 {
    font-size: 11px;
    color: #7B7B7B;
    margin: 0;
  }
  
  </style>
  