<template>
        <div class="CardProfilHover">
            <div class="header_card">
                <div class="iconCoverfit">
                    <img src="~/assets/profil_cr.jpg" alt="" width="20px" height="auto"/>
                </div>
                <div class="identity">
                    <div class="name_surname">
                        <h3>Pierre</h3>
                        <h3>Courtois</h3>
                    </div>
                    <h4>ING 4 - Grp03 </h4>
                </div>
            </div>
            <div class="metrics">
                <div class="intern_metrics">
                    <h5>Rôle :</h5>
                    <h6>Utilisateur</h6>
                </div>
                <div class="intern_metrics">
                    <h5>Rôle :</h5>
                    <h6>Utilisateur</h6>
                </div>
            </div>
        </div>
  </template>
  
  <script setup>

  </script>

<style>
body{
    background-color: beige;
}

h3{
    color: black;
    font-family: "Gilroy-Medium", sans-serif;
}

h4{
    color: #7B7B7B;
    font-family: "Gilroy-Regular";
    font-size: 0.8rem;
}


h5{
    color: #7B7B7B;
    font-family: "Gilroy-Regular";
}

h6{
    color: black;
    font-family: "Gilroy-Regular";
    font-size: 0.8rem;
}

.CardProfilHover{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    padding: 2rem;
    width: 20rem;
    height: auto;
    border-radius: 1rem;
    background-color: white;
    transition: box-shadow 0.3s ease-in-out;

}

.CardProfilHover:hover {
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* Ombre uniquement en bas */
}

.iconCoverfit {
    display: flex;

    border: 1px solid black;
    width: 2.5rem;
    border-radius: 50%;
    overflow: hidden;
    justify-content: center;
    align-items: center;
    height: 2.5rem;
  }
  .iconCoverfit img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .header_card{
    display: flex;
    flex-direction: row;
  }

  .identity{
    margin-left: 1rem;
  }

  .name_surname{
    display: flex;
    flex-direction: row;
    gap: 0.5rem;
  }

  .metrics{
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    gap: 0.6rem;
  }

  .intern_metrics{
    display: flex;
    flex-direction: row;
    gap: 1rem;
  }

</style>
  