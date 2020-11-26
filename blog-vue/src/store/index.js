import Vue from 'vue'
import Vuex from 'vuex'
import actions from './actions';
import mutations from './mutations'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginState: false,
    username: "世界天际",
    categoryList: []
  },
  mutations: mutations,
  actions: actions,
  modules: {
  }
})
