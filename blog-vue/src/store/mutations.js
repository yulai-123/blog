let mutations = {
    setName (state, name) {
        state.username = name
    },
    setLoginState (state, loginState) {
        state.loginState = loginState
    },
    setCategoryList (state, categoryList) {
        state.categoryList = categoryList
    }
}

export default mutations
