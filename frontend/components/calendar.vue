<template>
  <div class="calendar">
    <div class="calendar-header">
      <div class="calendar-month-year-container">
        <h2>{{ currentMonth }}</h2>
      </div>
      <div class="calendar-navigation">
        <button @click="prevMonth">
          <img src="~/public/assets/gauche.png" alt="Mois précédent" />
        </button>
        <button @click="nextMonth">
          <img src="~/public/assets/droite.png" alt="Mois suivant" />
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
          :key="day.date"
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

        <replay />
      </div>
    </div>
  </div>
</template>

<script>
import { format, startOfMonth, endOfMonth, startOfWeek, endOfWeek, addDays, addMonths, subMonths, startOfToday, isSameDay, isSameMonth } from 'date-fns';
import replay from './replay.vue';


export default {
  data() {
    return {
      currentDate: startOfToday(),
      selectedDate: null,
      weekdays: ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'],
    };
  },
  computed: {
    currentMonth() {
      return format(this.currentDate, 'MMMM yyyy');
    },
    days() {
      const start = startOfWeek(startOfMonth(this.currentDate));
      const end = endOfWeek(endOfMonth(this.currentDate));
      let day = start;
      const days = [];
      while (day <= end) {
        days.push({ date: day, label: format(day, 'd') });
        day = addDays(day, 1);
      }
      return days;
    },
  },
  methods: {
    prevMonth() {
      this.currentDate = subMonths(this.currentDate, 1);
    },
    nextMonth() {
      this.currentDate = addMonths(this.currentDate, 1);
    },
    isSelected(date) {
      return this.selectedDate && isSameDay(date, this.selectedDate);
    },
    isInCurrentMonth(date) {
      return isSameMonth(date, this.currentDate);
    },
    selectDay(date) {
      this.selectedDate = date;
      console.log('Jour sélectionné:', format(date, 'yyyy-MM-dd'));
    },
  },
};
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

.calendar-weekdays{
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
  text-align: center;
}

.events h2 {
  font-size: clamp(1rem, 2vw, 1.3rem);
}

@media screen and (max-width: 1024px) {
  .calendar{
    margin: 0rem;
  }
}
</style>
