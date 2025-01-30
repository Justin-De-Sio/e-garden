<template>
  <div class="calendar">
    <div class="calendar-header">
      <div class="calendar-month-year-container">
        <div class="calendar-month-year">
          {{ currentMonth }}
        </div>
        <button class="today-button" @click="goToToday">Aujourd'hui</button>
      </div>
      <div class="calendar-navigation">
        <button @click="prevMonth">
          <img src="/assets/gauche.png" alt="Mois précédent" />
        </button>
        <button @click="nextMonth">
          <img src="/assets/droite.png" alt="Mois suivant" />
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
  </div>
</template>

<script>
import { format, startOfMonth, endOfMonth, startOfWeek, endOfWeek, addDays, addMonths, subMonths, startOfToday, isSameDay, isSameMonth } from 'date-fns';
import { fr } from 'date-fns/locale';

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
      const month = format(this.currentDate, 'MMMM', { locale: fr });
      const year = format(this.currentDate, 'yyyy', { locale: fr });
      return `${month.charAt(0).toUpperCase() + month.slice(1)} ${year}`;
    },
    days() {
      const start = startOfWeek(startOfMonth(this.currentDate));
      const end = endOfWeek(endOfMonth(this.currentDate));
      const days = [];
      let day = start;
      while (day <= end) {
        days.push({
          date: day,
          label: format(day, 'd', { locale: fr }),
        });
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
    goToToday() {
      this.currentDate = startOfToday();
      this.selectedDate = startOfToday();
    },
    isSelected(date) {
      return this.selectedDate && isSameDay(date, this.selectedDate);
    },
    isInCurrentMonth(date) {
      return isSameMonth(date, this.currentDate);
    },
    selectDay(date) {
      this.selectedDate = date;
      this.onDayClick(date);
    },
    onDayClick(date) {
      // Votre fonction personnalisée ici
      console.log('Jour sélectionné:', format(date, 'yyyy-MM-dd'));
    },
  },
};
</script>

<style scoped>
.calendar {
  width: 20rem;
  border: 1px solid #ccc;
  border-radius: 15px;
  padding: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.calendar-navigation {
  display: flex;
  gap: 5px;
}
.calendar-navigation button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
}
.calendar-navigation img {
  width: 20px;
  height: 20px;
}
.calendar-month-year-container {
  text-align: center;
}
.calendar-month-year {
  font-size: 1.2em;
  font-weight: bold;
}
.today-button {
  margin-top: 5px;
  padding: 2px 5px;
  font-size: 0.8em;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  background-color: #f0f0f0;
}
.calendar-body {
  margin-top: 10px;
}
.calendar-weekdays {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
  margin-bottom: 10px;
}
.calendar-weekday {
  text-align: center;
  color: #7F7F7F;
}
.calendar-days {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
}
.calendar-day {
  text-align: center;
  padding: 5px;
  cursor: pointer;
  border-radius: 5px;
  position: relative;
  border: none;
}
.calendar-day.selected {
  border: 3px solid #49DC82;
  color: #49DC82;
}
.calendar-day.not-in-month {
  color: #7F7F7F;
}
</style>
