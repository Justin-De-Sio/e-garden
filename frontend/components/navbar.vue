<template>
    <nav :class="{expanded : IsExpanded}">
      <div class="placement_icon_navbar">
        <div class="logo_placement">
          <NuxtLink to="/login" class="logo-link">
            <img src="../assets/logo.png" alt="Logo" width="80" height="auto" />
          </NuxtLink>
        </div>
        <div class="expand_navbar" @click="toggleMenu">
          <div class="circle_around_angle">
              <img :class="{ rotated: IsExpanded }" src="../assets/angle.png" alt="Angle" width="25px" height="25px" />
          </div>
        </div>
        <div class="project_placement" :class="{expanded: IsExpanded}">
          <NuxtLink to="/" >
            <div class="main_wrapper" :class="{expanded: IsExpanded}">
              <div class="wrapper_img":class="{expanded: IsExpanded}" >
                <img src="../assets/applications.png" alt="Application" />
              </div>
              <h4>Dashboard</h4>
            </div>
          
          </NuxtLink>
          <NuxtLink to="/">
            <div class="main_wrapper" :class="{expanded: IsExpanded}">
              <div class="wrapper_img" :class="{expanded: IsExpanded}">
                <img src="../assets/camera.png" alt="Camera" />
              </div>
              <h4>Sécurité</h4>
            </div>
            
          </NuxtLink>
          <NuxtLink to="/">
            <div class="main_wrapper" :class="{expanded: IsExpanded}">
              <div class="wrapper_img":class="{expanded: IsExpanded}">
                <img src="../assets/capteur.png" alt="Capteur" />
              </div>
              <h4>Capteurs</h4>
            </div>
            
          </NuxtLink>
          <NuxtLink to="/">
            <div class="main_wrapper" :class="{expanded: IsExpanded}">
              <div class="wrapper_img" :class="{expanded: IsExpanded}">
                <img src="../assets/gestion.png" alt="Gestion" />
              </div>
              <h4>Gestionnaire</h4>
            </div>
          </NuxtLink>
        </div>
      </div>
    </nav>
  </template>
  
  <script>
  export default {
    data(){
      return {
        IsExpanded: false,
      };
    },
    methods : {
      toggleMenu(){
        this.IsExpanded = !this.IsExpanded;
      },
    },
  };
  </script>
  
  <style lang="css" scoped>
  nav {
    display: flex;
    justify-content: center;
    padding: 0 30px;
    width: 125px;
    height: 100vh;
    background-color: #151b11;
    position: relative;
    transition: width 0.3s ease;
  }
  
  nav.expanded{
    width: 270px;

  }
  .logo_placement {
    margin-top: 5rem;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  .project_placement {
    display: grid;
    grid-template-columns: 1fr;
    gap: 1.2rem;
    justify-items: center;
    margin-top: 5rem;
  }

  .project_placement.expanded{
    display: grid;
    grid-template-columns: 1fr;
    gap: 1.5em;
    justify-items: start;
    margin-top: 5em;
  }
  
  .project_placement img {
    width: 25px;
    height: auto;
    cursor: pointer;
  }
  

  .wrapper_img{
    display: flex;
    justify-content : center;
    width: 100%;
    padding: 20px;

  }

  .wrapper_img.expanded{
    display: flex;
    justify-content : center;
    padding: 20px;
    padding-left: 30px;
    width: 0;
  }


  
  .expand_navbar {
    position: absolute;
    right: -1em; 
    display: flex;
    cursor: pointer;
  }
  
  .circle_around_angle {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    background-color: #151B11;
    border-radius: 50%;
    width: 3em;
    height: 3em;
  }
  
  .circle_around_angle img {
    transform: rotate(-90deg);
    margin-right: 3px;
    display: block;
  }

  .main_wrapper{
    display: flex;
    flex-direction: row;
    justify-items: center;
    align-items: center;
    border-radius: 15px;
  }

  .main_wrapper.expanded{
    display: flex;
    align-items: left;
    justify-content: flex-start;
    padding-right: 2em;
  }

  h4{
    display: none;
    color: white;
    text-decoration: none;
    font-family: "Gilroy-Medium";
  }

  nav.expanded h4 {
  display: block;
  margin-left: 20px;
  }


  a{
  text-decoration: none;
  color: inherit;
  width: 100%;
  }

  a.logo-link {
  width: auto; 
  }

  .circle_around_angle img.rotated {
  transform: rotate(90deg);
  }

  .main_wrapper:hover{
    background-color: #95BD75;
  

  }

  
  </style>
  