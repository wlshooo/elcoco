<template>
  <div id="clock">
    <i class="fa fa-calendar" aria-hidden="true"> {{ date }}</i>
    <br><br><br>
    <i class="fa-regular fa-clock">{{ time }}</i>
  </div>
</template>

<script>
export default {
  data() {
    return {
      week: ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'],
      time: '',
      date: '',
    }
  },
  mounted() {
    setInterval(this.updateTime, 1000)	// 1초마다 시간 갱신
  },
  methods: {
    updateTime() {
      var cd = new Date()
      // 날짜 출력
      this.date =
          this.zeroPadding(cd.getFullYear(), 4) +
          '/' +
          this.zeroPadding(cd.getMonth() + 1, 2) +
          '/' +
          this.zeroPadding(cd.getDate(), 2) +
          ' ' +
          this.week[cd.getDay()]
      // 시간 출력
      this.time =
          this.zeroPadding(cd.getHours(), 2) +
          ':' +
          this.zeroPadding(cd.getMinutes(), 2) +
          ':' +
          this.zeroPadding(cd.getSeconds(), 2)
    },
    zeroPadding(num, digit) {
      var zero = ''
      for (var i = 0; i < digit; i++) {
        zero += '0'
      }
      return (zero + num).slice(-digit)
    },
  },
}
</script>

<style scoped>
#clock {
  font-size: 0.875rem;
  font-weight: 500;

}
</style>
