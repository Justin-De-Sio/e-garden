<template>

    <div class="report_container">
      <div 
        class="grid_report_person" 
        v-for="(item, index) in report.content" 
        :key="item.id"
      >
        <div class="wrapper_content">
          <div class="profil_card">
            <img src="~/assets/profil_cr.jpg" alt="" />
          </div>
          <div class="content_profil_report">
            <div class="placement_cr_icon">
              <h3>{{ item.user.name }}</h3>
              <img 
                :src="item.content ? '/_nuxt/assets/cr_done.png' : '/_nuxt/assets/cr_empty.png'" 
                alt="icon" 
                width="15vw" 
                height="auto"  />
              <button 
                class="toggle_button" 
                @click="toggleExpand(index)"
              >
                <img 
                  src="../assets/angle-petit.png" 
                  :class="{ rotated: expandedItems[index] }" 
                  alt="angle" 
                  width="15px" 
                  height="auto"
                >
              </button>
            </div>
            <div 
              class="content_wrapper" 
              ref="contentRefs" 
              :style="{ height: expandedItems[index] ? calculatedHeights[index] + 'px' : '0px' }"
            >
            <p>{{ item.content }}</p>
            </div>
          </div>
        </div>
        <div class="separation_line"></div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, nextTick } from 'vue';
  

  const expandedItems = ref([]);
  const calculatedHeights = ref([]); 
  
  defineProps({
    report: {
      type: Object,
    },
  });
  
  const contentRefs = ref([]); 
  
  function calculateHeights() {
    nextTick(() => {
      calculatedHeights.value = contentRefs.value.map((ref) => {
        return ref ? ref.scrollHeight : 0;
      });
    });
  }
  
  function toggleExpand(index) {
    expandedItems.value[index] = !expandedItems.value[index];
  }


  

  onMounted(() => {
    calculateHeights();
  });
  </script>
  
  <style scoped>

  .report_container {
    margin-top: 1rem;
    width: 100%;
    height: 100%;
    background-color: white;
    border-radius: 1rem;
    overflow-y: auto;
  }
  
  h2 {
    font-family: "Gilroy-Medium", sans-serif;
    font-size: clamp(0.9rem, 2vw, 1.5rem);
    margin-bottom: 1vh;
  }

  .grid_report_person:nth-child(1) {
    width: 100%;
    padding-top: 1rem;
  }
  
  .grid_report_person {
    width: 100%;
    margin-top: 0.5rem;
    padding: 0 0.9rem;
  }
  
  .wrapper_content {
    display: flex;
    flex-direction: row;
    padding: 1rem 0 0.5rem 1rem;
    width: 100%;
    height: 100%;
  }
  
  .profil_card {
    display: flex;
    margin: 0 1rem;
    border: 1px solid black;
    width: 2.5rem;
    border-radius: 50%;
    overflow: hidden;
    justify-content: center;
    align-items: center;
    height: 2.5rem;
  }
  
  .profil_card img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .placement_cr_icon {
    display: flex;
    align-items: center;
    flex-direction: row;

  }
  
  .placement_cr_icon h3 {
    margin-right: 0.5rem;
  }
  
  .content_profil_report {
    flex: 1;
  }
  
  .content_wrapper {
    margin-top: 0.5rem;
    overflow: hidden;
    height: 0; 
    transition: height 0.3s ease-in-out;
    overflow-y: auto;
  }

  .content_wrapper p{
    color: #7B7B7B;
    font-family: 'Gilroy-Regular';
    font-size: 0.8rem;
    margin-right: 1rem;
    text-align: justify;

  }



  .toggle_button{
    cursor: pointer;
    margin-left: auto;
    margin-right: 2rem;
    background: none;
    border: none;
  }

  .toggle_button img.rotated {
    transform: rotate(180deg);
  }
  
  .separation_line {
    width: 85%;
    height: 1px;
    background-color: #c8c8c8;
    margin: auto;
  }
  </style>
  