<template>
    <div class="report_container">
      <div 
        class="grid_report_person" 
        v-for="(item, index) in report.content" 
        :key="item.id"
      >
        <div 
          class="wrapper_content" 

        >
          <div class="profil_card"           
          @mouseenter="toggleHover(index, true)" 
          @mouseleave="toggleHover(index, false)">
            <img src="~/assets/profil_cr.jpg" alt="profil" />
          </div>

          <div class="page_overlay" 
            v-if="hoveredIndex === index">
          </div>
  
          <HoverCard 
            class="HoverTheCard" 
            v-if="hoveredIndex === index"
          ></HoverCard>
  
          <div class="content_profil_report">
            <div class="placement_cr_icon">
              <h3>{{ item.user.name }}</h3>
              <img 
                :src="item.content ? '/_nuxt/assets/cr_done.png' : '/_nuxt/assets/cr_empty.png'" 
                alt="icon" 
                width="15vw" 
                height="auto" 
              />
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
                />
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
  import HoverCard from "~/components/identityCard.vue";
  
  const expandedItems = ref([]);
  const calculatedHeights = ref([]);
  const hoveredIndex = ref(null);
  
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
  
  function toggleHover(index, isHovering) {
    hoveredIndex.value = isHovering ? index : null;
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
    position: relative;
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
    position: relative;
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
  
  .HoverTheCard {
    position: absolute;
    top: 0;
    left: 0.5rem;
    width: 300px;
    background: white;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
    border-radius: 0.5rem;
    padding: 1rem;
    z-index: 100;
  }
  
  .page_overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background: rgba(0, 0, 0, 0.5); 
    z-index: 99;
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
  
  .content_wrapper p {
    color: #7b7b7b;
    font-family: 'Gilroy-Regular';
    font-size: 0.8rem;
    margin-right: 1rem;
    text-align: justify;
  }
  
  .toggle_button {
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
  