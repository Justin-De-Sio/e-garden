<template>
  <div class="calendar">
    <div class="calendar-header">
      <div class="calendar-month-year-container">
        <h2>{{ currentMonth }}</h2>
      </div>
      <div class="calendar-navigation">
        <button @click="prevMonth">
          <img src="~/public/assets/gauche.png" alt="Mois précédent"/>
        </button>
        <button @click="nextMonth">
          <img src="~/public/assets/droite.png" alt="Mois suivant"/>
        </button>
      </div>
    </div>
    <div class="calendar-body">
      <div class="calendar-weekdays">
        <div v-for="day in weekdays" :key="day" class="calendar-weekday">
          {{ day }}
        </div>
      </div>
      <div class="calendar-days">
        <div
            v-for="day in days"
            :key="day.date.toISOString()"
            :style="{ fontWeight: isPresentVideo(day.date) ? 'bold' : 'normal' }"
            class="calendar-day"
            :class="{ selected: isSelected(day.date), 'not-in-month': !isInCurrentMonth(day.date) }"
            @click="selectDay(day.date)"
        >
          {{ day.label }}
        </div>
      </div>
    </div>
    <div class="events">
      <h2>Évènements</h2>
      <div class="wrapper_content">
        <Replay :day="selectedDateDay" :month="selectedDateMonth" :year="selectedDateYear" :isVideo="isPresentVideo(selectedDate)"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from "vue";
import {
  addDays,
  addMonths,
  endOfMonth,
  endOfWeek,
  format,
  isSameDay,
  isSameMonth,
  startOfMonth,
  startOfToday,
  startOfWeek,
  subMonths
} from "date-fns";
import {callAPIServices} from "~/services/callAPIServices";
import type {Video} from "~/model/Videos";

// 🗓️ États réactifs
const currentDate = ref<Date>(startOfToday());
const selectedDate = ref<Date | null>(null);
const video = ref(null);
const weekdays = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"];

// 📅 Calcul du mois actuel
const currentMonth = computed(() => format(currentDate.value, "MMMM yyyy"));

const selectedDateYear = computed(() => format(selectedDate.value, "yyyy"));
const selectedDateMonth = computed(() => format(selectedDate.value, "MM"));
const selectedDateDay = computed(() => format(selectedDate.value, "dd"));


// 📆 Calcul des jours du mois affiché
const days = computed(() => {
  const start = startOfWeek(startOfMonth(currentDate.value));
  const end = endOfWeek(endOfMonth(currentDate.value));
  let day = start;
  const daysArray = [];

  while (day <= end) {
    daysArray.push({date: day, label: format(day, "d")});
    day = addDays(day, 1);
  }

  return daysArray;
});

// 🔄 Navigation entre les mois
const prevMonth = async () => {
  currentDate.value = subMonths(currentDate.value, 1);
  await getVideoFile();
};
const nextMonth = async () => {
  currentDate.value = addMonths(currentDate.value, 1);
  await getVideoFile();
};

function isPresentVideo(date: Date): boolean {
  // Vérifiez si 'video.value' est défini et si nous avons des vidéos disponibles
  if (video.value && Array.isArray(video.value)) {
    // Formater la date sous forme "yyyy-MM-dd" pour la comparaison
    const formattedDate = format(date, "yyyy-MM-dd");

    // Vérifiez si la date formatée est présente dans l'une des vidéos
    return video.value.some((videoItem: Video) => videoItem.fileDate.split("T")[0] === formattedDate);
  }
  // Retourne false si aucune vidéo ou mauvais format
  return false;
}


// ✅ Vérifications sur les dates
const isSelected = (date: Date) => selectedDate.value && isSameDay(date, selectedDate.value);
const isInCurrentMonth = (date: Date) => isSameMonth(date, currentDate.value);

// 📌 Sélection d'un jour
const selectDay = (date: Date) => {
  selectedDate.value = date;
};

// 🎥 Récupération des vidéos
const getVideoFile = async () => {
  const api = new callAPIServices();
  try {
    const year = format(currentDate.value, "yyyy"); // Récupérer l'année
    const month = format(currentDate.value, "MM"); // Récupérer le mois
    video.value = await api.fetchAPIGet(`videos/month-videos?year=${year}&month=${month}`) as Video;
  } catch (error) {
    console.error("Les vidéos n'ont pas été récupérées pour le mois demandé", error);
  }
};

// 🚀 Chargement des vidéos au montage du composant
onMounted(() => {
  currentDate.value = startOfToday();
  getVideoFile();
});
</script>


<style scoped>
.calendar {
  width: 100%;
  max-width: 600px;
  height: 100%;
  margin: 2rem;
  padding: 2rem;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.calendar-navigation {
  display: flex;
  gap: 5px;
}

.calendar-navigation button {
  background: none;
  border: none;
  cursor: pointer;
}

.calendar-navigation img {
  width: 20px;
  height: 20px;
}

.calendar-month-year-container {
  text-align: center;
}

.calendar-month-year-container h2 {
  font-size: clamp(1rem, 2vw, 1.3rem);
  font-family: "Aeonik-Regular";

}

.calendar-body {
  align-self: center;
  width: 100%;
  margin-top: 10px;
}

.calendar-weekdays {
  margin: 2rem 0 1rem 0;
  color: #7F7F7F
}

.calendar-weekdays, .calendar-days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 3px;
}

.calendar-weekday, .calendar-day {
  text-align: center;
  font-size: clamp(0.8rem, 1.5vw, 1rem);
}

.calendar-day {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80%;
  height: 3.3rem;
  cursor: pointer;
  border-radius: 10px;

}

.calendar-day:hover {
  background-color: #95bd75;
  color: white;
  border-radius: 1rem;
}

.calendar-day.selected {
  border: 2px solid #95bd75;
  color: #95bd75;
  border-radius: 1rem;
}

.calendar-day.not-in-month {
  color: #7F7F7F;
}

.events {
  padding-top: 1.5rem;

  width: 100%;
}

.events h2 {
  font-size: clamp(1rem, 2vw, 1.3rem);
  text-align: center;
}

.wrapper_content {
  margin-top: 1.5rem;
  width: 100%;
}

@media screen and (max-width: 1024px) {
  .calendar {
    margin: 0rem;
  }
}
</style>
