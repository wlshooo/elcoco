<template>
  <Carousel v-model="currentIndex">
    <slide v-for="(image, index) in images" :key="index">
      <div class="image-container">
        <img :src="getImagePath(image)" class="slide-image"/>
      </div>
    </slide>

    <template #addons>
      <Navigation/>
      <Pagination/>
    </template>
  </Carousel>
</template>

<script>
import {defineComponent} from 'vue';
import {Carousel, Navigation, Pagination, Slide} from 'vue3-carousel';

import 'vue3-carousel/dist/carousel.css';

export default defineComponent({
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Basic',
  components: {
    Carousel,
    Slide,
    Pagination,
    Navigation,
  },
  data() {
    return {
      images: [
        'elcoco.png',
        'test.png',
        'test2.gif'
      ],
      currentIndex: 0,
    };
  },
  methods: {
    getImagePath(imageFileName) {
      return require(`@/assets/image/${imageFileName}`);
    },
  },
  created() {
    setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.images.length;
    }, 5000);
  },
});
</script>

<style>
.image-container {
  width: 80%; /* 전체 크기의 80%만 사용 */
  margin: 0 auto; /* 가운데 정렬 */
  padding: 0 5%; /* 왼쪽 10%, 오른쪽 10% 여백 추가 */
  box-sizing: border-box; /* 여백을 요소 크기에 포함시킴 */
  position: relative;
  overflow: hidden;
}

.slide-image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지 비율 유지 및 부족한 부분 채우기 */
}
</style>
