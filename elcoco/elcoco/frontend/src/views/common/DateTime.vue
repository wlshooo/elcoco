<template>
  <div id="centered-content">
    <div id="clock">
      <div class="weather-time-date">
        <div class="time-date-container">
          <Weather/>
          <i class="fa-regular fa-clock">{{ time }}</i>
          <i class="fa fa-calendar" aria-hidden="true"> {{ date }}</i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Weather from "@/views/common/Weather.vue";

export default {
  components: { Weather },
  data() {
    return {
      week: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
      time: "",
      date: "",
    };
  },
  mounted() {
    setInterval(this.updateTime, 1000); // 1초마다 시간 갱신
  },
  methods: {
    updateTime() {
      var cd = new Date();
      // 날짜 출력
      this.date =
          this.zeroPadding(cd.getFullYear(), 4) +
          "/" +
          this.zeroPadding(cd.getMonth() + 1, 2) +
          "/" +
          this.zeroPadding(cd.getDate(), 2) +
          " " +
          this.week[cd.getDay()];
      // 시간 출력
      this.time =
          this.zeroPadding(cd.getHours(), 2) +
          ":" +
          this.zeroPadding(cd.getMinutes(), 2) +
          ":" +
          this.zeroPadding(cd.getSeconds(), 2);
    },
    zeroPadding(num, digit) {
      var zero = "";
      for (var i = 0; i < digit; i++) {
        zero += "0";
      }
      return (zero + num).slice(-digit);
    },
  },
};
</script>

<style scoped>
#centered-content {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh;
}

#clock {
  font-size: 0.875rem;
  font-weight: 500;
}

.weather-time-date {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}


.time-date-container {
  display: inline;
}
</style>
