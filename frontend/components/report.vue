<template>
     <h2>Compte-Rendus</h2>
    <div class="report_container" >
        <div class="grid_report_person"
        v-for="(item, index) in report.content" 
            :key="item.id">
            <div class="wrapper_content"   >
                <div class="profil_card">
                    <img src="~/assets/profil_cr.jpg" alt="" width="35vw" height="auto">
                </div>
                <div class="content_profil_report">
                    <!-- <h3>{{ user_name }}</h3>-->
                    <div class="placement_cr_icon">
                        <h3>{{ item.id }}</h3>
                        <img src="~/assets/cr_done.png" alt="" width="15vw" height="auto">
                    </div>

                    <p>{{ item.content }}</p>

                    
                </div>
            </div>
            <div class="separation_line"></div>
        </div>

    </div>
</template>

<script setup>
defineProps({
    report: {
        type: Object, 
    }
    });

</script>

<style lang="css" scoped>

    .report_container{
        width: 100%;
        
        height: 100%;
        background-color: white;
    }

    h2{
        font-family: "Gilroy-Medium", sans-serif;
        font-size: clamp(0.9rem, 2vw, 1.5rem); 
        margin-bottom: 1vh;
    }
    
    .grid_report_person{
        width: 100%;
        padding: 0 0.8rem  ;
    }

    .grid_report_person:nth-child(1){
        width: 100%;
        padding-top: 1rem;
    }
    .wrapper_content{
        display: flex;
        flex-direction: row;
        padding: 1rem 0 0.5rem 1rem;
        width: 100%;
        height: 100%;

    }
    .profil_card{
        display: flex;
        align-self: center;
        margin: 0 1rem ;
        border: 1px solid black;
        width: 2.5rem;
        border-radius: 50%;
        overflow: hidden;
        justify-content: center;
        align-items: center;
        height: 2.5rem;
    }

    .profil_card img{
        object-fit: cover;
    }

    .placement_cr_icon{
        display: flex;
        flex-direction: row;
    }
    .placement_cr_icon h3{
        margin-right: 0.5rem;
    }

    .content_profil_report{

        flex: 1;

    }

    .content_profil_report p {
        color: #7B7B7B;
        font-family: "Gilroy-Regular";
        font-size: 0.8rem;
        max-height: 1.5rem; 
        overflow: hidden; 
        text-overflow: ellipsis;
        display: -webkit-box; 
        -webkit-box-orient: vertical; 
        -webkit-line-clamp: 1;
        line-height: 1.5rem;
}


    .separation_line{
        width: 85%;
        height: 1px;
        background-color: #c8c8c8;
        margin: auto;
    }
</style>