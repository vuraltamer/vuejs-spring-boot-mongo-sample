<script>
export default {
  name: 'models',
  person: {
    username: '',
    firstName: '',
    lastName: ''
  },
  searchResponse: {
    currentPage: 0,
    elementCount: 0,
    elements: []
  },
  searchRequest: {
    searchDto: {username: '', firstName: '', lastName: ''},
    pagination: {currentPage: 1, pageSize: 5},
    sortType: {sort: 'ASC', sortParameter: ''}
  }
}
</script>
