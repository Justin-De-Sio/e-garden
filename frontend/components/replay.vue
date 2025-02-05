<template>
    <div class="container">
        <div class="content">
            <div class="left_section">
                <div class="icon_back">
                    <img src="/assets/camera-replay.png" alt="">
                </div>
                <div class="inside_content">
                    <h2>Rediffusion</h2>
                    <h4>{{ day }} "-" {{ month }} "-" {{ year }}</h4>
                </div>
            </div>
            <div class="right_section">
                <div class="background_button">
                    <button type="button" @click="SeeVideo">Visionner</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    defineProps<{
        day: string | number;
        month: string;
        year: string | number;
    }>();

    async function SeeVideo(day: string | number, month: string, year: string | number) {
            
    } 
</script>

<style scoped>
.container {
    display: flex;
    background-color: #F2F4EF;
    width: 100%;
    height: auto;
    padding: 1rem;
    border-radius: 1rem;
}

.content {
    display: flex;
    justify-content: space-between;
    flex: 1;
    width: 100%;
    align-items: center;
}

.left_section {
    display: flex;
    flex-direction: row;
    flex: 3;
    align-items: center;
    gap: 1rem;
}

.icon_back {
    width: min(10vw, 3rem);
    height: min(10vw, 3rem);
    background-color: #95BD75;
    border-radius: 0.8rem;
    display: flex;
    align-items: center;
    justify-content: center;
}

.icon_back img {
    width: 50%;
    height: auto;
    object-fit: cover;
}

.inside_content {
    display: flex;
    flex-direction: column;
}

.inside_content h2 {
    font-size: clamp(0.8rem, 1.5vw, 1rem);
    font-family: "Aeonik-Medium";
}

.inside_content h4 {
    font-size: clamp(0.6rem, 1.5vw, 0.8rem);
    font-family: "Aeonik-Regular";
}

.right_section {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    flex: 1;
}

.background_button {

    width: auto;
    height: auto;
    padding: 0.3rem 1rem;
    border-radius: 0.3rem;
    background-color: #95BD75;
}

.background_button button {

    background: none;
    color: white;
    font-size: clamp(0.7rem, 1vw, 1rem);
    font-family: "Aeonik-Medium";
}

/* Responsive - Mobile */
@media screen and (max-width: 768px) {


    .icon_back {
        width: 2rem;
        height: 2rem;
        border-radius: 0.5rem;
        padding: 0.1rem;
    }

    .right_section {
        width: 100%;
        justify-content: center;
    }

    .background_button {
        width: 100%;
        text-align: center;
        padding: 0.2rem 0.6rem;
    }

    .background_button button {
        width: 100%;
    }
}
</style>
