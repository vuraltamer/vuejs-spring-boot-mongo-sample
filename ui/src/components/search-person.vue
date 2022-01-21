<template>
  <div>
    <div>
      <input v-model="searchRequest.searchDto.firstName" placeholder="firstName">
      <button v-on:click="searchData">Search</button>
    </div>
    <div>
      <table class="table" id="datatable">
        <thead>
        <tr>
          <th v-for="field in fields" :key="field.key" @click="sortData(field.key)">
          {{field.label}} <sort-icon/>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in responseData.elements" :key="item.id">
          <td>{{item.id}}</td>
          <td>{{item.username}}</td>
          <td>{{item.firstName}}</td>
          <td>{{item.lastName}}</td>
          <td>{{item.email}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="pagination-wrapper">
      <b-pagination
        v-model="searchRequest.pagination.currentPage"
        :total-rows="responseData.elementCount"
        :per-page="searchRequest.pagination.pageSize"
        @change="changePage"
        aria-controls="my-table"
      ></b-pagination>
    </div>
    <br>
    <br>
  </div>
</template>

<script>
import {BPagination, BModal, BTable, BCol, BFormSelect,
  BFormGroup, BButton, BFormInput, BInputGroup, BFormCheckbox,
  BFormCheckboxGroup, BRow, BContainer, BInputGroupAppend,
  BIcon, BIconArrowUp } from 'bootstrap-vue'
import services from '../services/services'
import models from './models/models'
import Paginate from 'vuejs-paginate'
import sortIcon from './icons/sort-icon'
export default {
  name: 'search-person',
  components: {
    sortIcon,
    bPagination: BPagination,
    bModal: BModal,
    bTable: BTable,
    bCol: BCol,
    bFormSelect: BFormSelect,
    bFormGroup: BFormGroup,
    bButton: BButton,
    bFormInput: BFormInput,
    bInputGroup: BInputGroup,
    bFormCheckbox: BFormCheckbox,
    bFormCheckboxGroup: BFormCheckboxGroup,
    bRow: BRow,
    bContainer: BContainer,
    bInputGroupAppend: BInputGroupAppend,
    paginate: Paginate,
    BIcon,
    BIconArrowUp
  },
  data () {
    return {
      searchRequest: models.searchRequest,
      responseData: models.searchResponse,
      fields: [
        { key: 'id', label: 'id', sortable: true, sortDirection: 'desc' },
        { key: 'username', label: 'username', sortable: true, sortDirection: 'desc' },
        { key: 'firstName', label: 'firstName', sortable: true, sortDirection: 'desc' },
        { key: 'lastName', label: 'lastName', sortable: true, sortDirection: 'desc' },
        { key: 'email', label: 'email', sortable: true, sortDirection: 'desc' }
      ]
    }
  },
  methods: {
    fetchData () {
      let self = this
      self.searchRequest.pagination.currentPage--
      services.postRequest().post('person/search', self.searchRequest)
        .then(function (response) {
          self.responseData = response.data
          self.searchRequest.pagination.currentPage = self.responseData.currentPage + 1
        })
        .catch(function (error) {
          console.log(error)
        })
      debugger
    },
    searchData () {
      debugger
      this.searchRequest.pagination.currentPage = 1
      this.fetchData()
    },
    changePage (event) {
      debugger
      this.searchRequest.pagination.currentPage = event
      this.fetchData()
    },
    sortData (event) {
      this.searchRequest.sortType.sort = this.searchRequest.sortType.sort === 'ASC' ? 'DESC' : 'ASC'
      this.searchRequest.sortType.sortParameter = event
      this.searchRequest.pagination.currentPage = 1
      this.fetchData()
      debugger
    }
  },
  mounted () {
    this.fetchData()
  }
}
</script>
<style lang="css">
</style>
