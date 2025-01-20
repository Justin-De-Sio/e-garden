<template>
  <div class="report_container">
    <!-- Si les données existent, affichez-les -->
    <div 
      v-if="reportData && reportData.content && reportData.content.length > 0"
    >
    <div class="pagination_controls">
      <button 
        @click="previousPage" 
        :disabled="currentPage === 0"
      ><img class="previous" src="@/assets/arrow_np.png" alt=""></button>
      <!--<span>{{ currentPage + 1 }}</span>-->
      <button 
        @click="nextPage" 
        :disabled="!reportData || !reportData.content || reportData.content.length < pageSize"
      ><img class="next" src="@/assets/arrow_np.png" alt=""></button>
    </div>
      <div 
        class="grid_report_person" 
        v-for="(item, index) in reportData.content" 
        :key="item.id"
      >
        <div class="wrapper_content">
          <div 
            class="profil_card"           
            @mouseenter="toggleHover(index, true)" 
            @mouseleave="toggleHover(index, false)"
          >
            <img src="~/assets/profil_cr.jpg" alt="profil" />
          </div>

          <div 
            class="page_overlay" 
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

    <div v-else>
      <p>Aucun rapport disponible.</p>
    </div>

   
  </div>
</template>

  
  <script setup>
  import { ref, onMounted, watch } from 'vue';
  import HoverCard from "~/components/identityCard.vue";
  import { fetchBackend } from "~/services/call_backend";
  
  const reportData = ref(null); 
  const currentPage = ref(0); 
  const pageSize = ref(5); 
  const expandedItems = ref([]);
  const calculatedHeights = ref([]);
  const hoveredIndex = ref(null);
  const contentRefs = ref([]);
  
  // Fonction pour récupérer les données depuis le backend
  async function fetchReports(page) {
    try {
      const data = await fetchBackend('/api/report/', page, pageSize.value);
      reportData.value = data;
      console.log("Données reçues :", reportData.value);
    } catch (error) {
      console.error('Erreur lors de la récupération des rapports :', error);
    }
  }
  
  function toggleExpand(index) {
    expandedItems.value[index] = !expandedItems.value[index];
  }
  
  function toggleHover(index, isHovering) {
    hoveredIndex.value = isHovering ? index : null;
  }
  

  function nextPage() {
    currentPage.value++;
  }
  
  function previousPage() {
    if (currentPage.value > 0) currentPage.value--;
  }
  
  watch(currentPage, (newPage) => {
    fetchReports(newPage);
  });
  

  onMounted(() => {
    fetchReports(currentPage.value);
    fetchReports(console.log(currentPage.value));
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
  .pagination_controls {
    display: flex;
    justify-content: flex-end; 
    align-items: center;
    gap: 0.5rem; 
    margin-top: 1rem; 
    margin-right: 1rem; 
  }

  .pagination_controls button{
    background: none;
    border: none;
    cursor: pointer;
  }

  .pagination_controls button img{
    width: 1rem;
  }

  .previous{
    transform: rotate(90deg);
  }

  .next{
    transform: rotate(-90deg);
  }
  </style>
  