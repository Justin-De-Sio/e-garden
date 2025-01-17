<script setup lang="ts">
import gestion_tab from '~/components/gestion_tab.vue';

const columns =
    [{
    key: 'Nom',
    label: 'Nom'
    }, {
    key: 'Prénom',
    label: 'Prénom',
    }, {
    key: 'email',
    label: 'Email',
    }, {
    key: 'Classe',
    label: 'Classe',
    }, {
    key: 'Groupe',
    label: 'Groupe',
    }, {
    key: 'role',
    label: 'Rôle'
    }, {
    key: 'Action',
    label: 'Action'
    }]

const people = [
  {
    "Nom": "Petit",
    "Prénom": "Jean",
    "email": "user0@example.com",
    "Classe": "Physique",
    "Groupe": "A",
    "role": "Étudiant",
    "Action": "None"
  },
  {
    "Nom": "Bernard",
    "Prénom": "Luc",
    "email": "user1@example.com",
    "Classe": "Mathématiques",
    "Groupe": "B",
    "role": "Professeur",
    "Action": "None"
  },
  {
    "Nom": "Thomas",
    "Prénom": "Sophie",
    "email": "user2@example.com",
    "Classe": "Chimie",
    "Groupe": "C",
    "role": "Étudiant",
    "Action": "None"
  },
  {
    "Nom": "Martin",
    "Prénom": "Pierre",
    "email": "user3@example.com",
    "Classe": "Biologie",
    "Groupe": "A",
    "role": "Professeur",
    "Action": "None"
  },
  {
    "Nom": "Robert",
    "Prénom": "Marie",
    "email": "user4@example.com",
    "Classe": "Informatique",
    "Groupe": "B",
    "role": "Étudiant",
    "Action": "None"
  },
  {
    "Nom": "Petit",
    "Prénom": "Luc",
    "email": "user5@example.com",
    "Classe": "Physique",
    "Groupe": "C",
    "role": "Professeur",
    "Action": "None"
  },
  {
    "Nom": "Bernard",
    "Prénom": "Jean",
    "email": "user6@example.com",
    "Classe": "Mathématiques",
    "Groupe": "A",
    "role": "Étudiant",
    "Action": "None"
  },
  {
    "Nom": "Thomas",
    "Prénom": "Pierre",
    "email": "user7@example.com",
    "Classe": "Chimie",
    "Groupe": "B",
    "role": "Professeur",
    "Action": "None"
  },
  {
    "Nom": "Martin",
    "Prénom": "Sophie",
    "email": "user8@example.com",
    "Classe": "Biologie",
    "Groupe": "C",
    "role": "Étudiant",
    "Action": "None"
  },
  {
    "Nom": "Robert",
    "Prénom": "Luc",
    "email": "user9@example.com",
    "Classe": "Informatique",
    "Groupe": "A",
    "role": "Professeur",
    "Action": "None"
  }
]
</script>

<template>
  <div class="wrapper_page">
  <gestion_tab :columns="columns" :rows="people" />
    </div>
</template>

<style>
.wrapper_page {
  display: flex;
  width: 100%;
  max-width: 100%;
  height: 100vh;
  height: 100dvh;
}


</style>