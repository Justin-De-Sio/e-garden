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

    .header{
        display: flex;
        flex-direction: column;
    }

    .header h1{
        font-family: "Gilroy-Bold";
        color: black;
        font-size: clamp(1.4rem, 2.5vw, 2rem);
    }
    .header h3{
        font-family: "Gilroy-Medium";
        color: #969696;
        font-size: clamp(0.8rem, 1.3vw, 1rem);
    }
</style>