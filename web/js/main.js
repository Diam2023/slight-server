window.onload = () => {
  let app = new Vue({
    el: '#SlightServer',
    data: {
      message: '',
      version: '',
    },
    mounted() {
      this.$http.get('./data/info.json').then((res) => {
        this.message = res.data.message;
        this.version = '——' + res.data.version;
        let element = document.querySelector('#SlightServer');
        setTimeout(() => {
          element.style.opacity = '1';
        }, 1000);
      });
    },
  });
};
