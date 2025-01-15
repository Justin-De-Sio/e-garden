<template>
    <div class="header">
        <h1>{{ title }}</h1>
        <h3>{{ subtitle }}</h3>
    </div>
</template>

<script setup>
    defineProps({
        title:{
            type:String,
            required:true,
        },
        subtitle:{
            type:String,
            required: true,
        }
    });
    
</script>

<style lang="css" >

    h1{
        font-family: "Gilroy-Bold";
        color: black;
        font-size: 2rem;
    }
    h3{
        font-family: "Gilroy-Medium";
        color: #969696;
        font-size: 1rem;
    }
</style>