<template>
    <nav>
      <div class="placement_icon_navbar">
        <div class="logo_placement">
          <NuxtLink to="/">
            <img src="../assets/logo.png" alt="Logo" width="80" height="auto" />
          </NuxtLink>
        </div>
        <div class="expand_navbar">
          <div class="circle_around_angle">
            <img src="../assets/angle.png" alt="Angle" width="25px" height="25px" />
          </div>
        </div>
        <div class="project_placement">
          <NuxtLink to="/">
            <img src="../assets/applications.png" alt="Application" />
          </NuxtLink>
          <NuxtLink to="/">
            <img src="../assets/camera.png" alt="Camera" />
          </NuxtLink>
          <NuxtLink to="/">
            <img src="../assets/capteur.png" alt="Capteur" />
          </NuxtLink>
          <NuxtLink to="/">
            <img src="../assets/gestion.png" alt="Gestion" />
          </NuxtLink>
        </div>
      </div>
    </nav>
  </template>
  
  <script>
  export default {};
  </script>
  
  <style lang="css" scoped>
  nav {
    display: flex;
    justify-content: center;
    width: 125px;
    height: 100vh;
    background-color: #151b11;
    position: relative;
  }
  
  .logo_placement {
    margin-top: 5em;
    display: flex;
    justify-content: center;
  }
  
  .project_placement {
    display: grid;
    grid-template-columns: 1fr;
    gap: 3em;
    justify-items: center;
    margin-top: 5em;
  }
  
  .project_placement img {
    width: 25px;
    height: auto;
    cursor: pointer;
    transition: transform 0.2s ease;
  }
  
  .project_placement img:hover {
    transform: scale(1.2);
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



  </style>
  