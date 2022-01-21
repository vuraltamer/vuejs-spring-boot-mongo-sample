<script>
import axios from 'axios'

export default {
  name: 'services',
  urlVar: '',
  getRequest () {
    return {
      get: (urlVar) => axios
        .get('http://localhost:8055/api/' + urlVar)
    }
  },
  postRequest () {
    return {
      post: (urlVar, body) => axios
        .post('http://localhost:8055/api/' + urlVar, body)
    }
  }
}
</script>
