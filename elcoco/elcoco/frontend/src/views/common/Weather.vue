<template>
  <div id="weather">
    <!-- 날씨 정보가 있을 경우 -->
    <div v-if="typeof weather.main != 'undefined'">
      <div :title="weather.weather[0].main">
        <!-- Clear -->
        <v-icon v-if="code === 800"><Icon icon="mdi:weather-sunny" /></v-icon>
        <!-- Thunderstorm -->
        <v-icon v-else-if="code.substr(0, 1) === 2">
          <Icon icon="mdi:weather-lightning-rainy" />
        </v-icon>
        <!-- Drizzle -->
        <v-icon v-else-if="code.substr(0, 1) === 3">
          <Icon icon="mdi:weather-rainy" />
        </v-icon>
        <!-- Rain -->
        <v-icon v-else-if="code.substr(0, 1) === 5">
          <Icon icon="mdi:weather-pouring" />
        </v-icon>
        <!-- Snow -->
        <v-icon v-else-if="code.substr(0, 1) === 6">
          <Icon icon="mdi:weather-snowy" />
        </v-icon>
        <!-- Atmosphere -->
        <v-icon v-else-if="code.substr(0, 1) === 7">
          <Icon icon="mdi:weather-fog" />
        </v-icon>
        <!-- Clouds -->
        <v-icon v-else>
          <Icon icon="mdi:weather-cloudy" />
        </v-icon>
        {{ Math.round(temp) }}℃</div>

    </div>
    <!-- 날씨 정보가 없을 경우 -->
    <div v-else>
      <div>
        <v-icon>mdi-cancel</v-icon>
      </div>
      <div>
        {{ weather.cod }}
      </div>
    </div>
  </div>
</template>
<script>
import { Icon } from '@iconify/vue';
export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    Icon,
  },
  data: function() {
    return {
      api_key: '823e9e3fb2de2c02b7c35bcf4c105b9a',
      url_base: 'https://api.openweathermap.org/data/2.5/',
      weather: {},
      code: '',
      temp: 0,

    }
  },
  mounted() {
    // API 요청 URL (지역 Seoul 고정)
    let fetchUrl = `${this.url_base}weather?q=Seoul&units=metric&APPID=${this.api_key}`
    fetch(fetchUrl)
        .then(response => {
          return response.json()
        })
        .then(result => {
          this.weather = result // 날씨 정보
          this.temp = result.main.temp // 기온
          this.code = result.weather[0].id.toString() // 날씨 코드
        })
  },
}
</script>

<style scoped>
#weather {
  font-size: 2rem;
  font-weight: 500;
  text-align: right;
  padding-right: 20px;
}
</style>