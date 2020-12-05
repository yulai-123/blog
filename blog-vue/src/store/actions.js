import axios from "axios";

axios.defaults.baseURL = "http://localhost:8085"
axios.defaults.withCredentials = true

let actions = {
    register: function (context, data) {
        axios.post('/register', {
            name: data.name,
            passwd: data.passwd
        }).then(function (response) {
            let result = response.data;
            let code = result.code;
            if(code === 0) {
                alert("注册成功")
                data.$router.push("/login")
            } else if(code === 101) {
                alert("用户名已存在，请重新注册")
            } else if(code === 111) {
                alert("用户名或密码不符合要求，请重新填写")
            } else {
                alert(result.message)
            }
        }).catch(function () {
            alert("注册失败")
        })
    },
    login: function (context, data) {
        axios.post('/login', {
            name: data.name,
            passwd: data.passwd
        }).then(function (response) {
            let result = response.data;
            let code = result.code;
            if(code === 0) {
                context.commit("setName", data.name)
                context.commit("setLoginState", true)
                if(window.history.length >= 3) {
                    data.$router.go(-1)
                } else {
                    data.$router.push("/")
                }
            } else if(code === 111) {
                alert("用户名或密码不符合要求，请重新填写")
            } else {
                alert(result.message)
            }
        }).catch(function () {
            alert("登录失败")
        })
    },
    logout: function (context) {
        axios.post('/logout').then(function (response) {
            let result = response.data;
            let code = result.code;
            if(code === 0) {
                context.commit("setName", "")
                context.commit("setLoginState", false)
            } else {
                alert(result.message)
            }
        }).catch(function () {
            alert("退出失败")
        })
    },
    getCategoryList: function (context) {
        axios.get("/categorylist").then((response) => {
            let result = response.data
            let code = result.code
            if(code === 0) {
                let categoryList = result.data.categoryList
                context.commit("setCategoryList", categoryList)
            } else {
                console.log(result.message)
            }
        })
    },
    addNewCategory: function (context, data) {
        let newCategoryName = data.newCategoryName
        let $message = data.$message
        axios.post("/category", {
            name: newCategoryName
        }).then((response) => {
            let result = response.data
            let code = result.code
            if(code === 0) {
                context.dispatch('getCategoryList')
                $message({
                    type: 'success',
                    message: '新增分类：' + data.newCategoryName
                });
            } else {
                // console.log(result.message)
                $message.error(result.message)
            }
        }).catch(() => {
            this.$message.error("新增分类失败")
        })
    },
    checkLoginState: function (context) {
        axios.post("/checkloginstate").then((response) => {
            let result = response.data
            let code = result.code
            let data = result.data
            if(code === 0) {
                context.commit("setName", data.name)
                context.commit("setLoginState", true)
            } else if(code === 141) {
                context.commit("setName", "")
                context.commit("setLoginState", false)
            } else {
                console.log(result.message)
            }
        }).catch(() => {
            console.log("查询登录状态出错")
        })
    },
    addNewArticle: function (context, data) {
        let $message = data.$message
        let $router = data.$router
        axios.post("/article", {
            title: data.title,
            content: data.content,
            categoryName: data.categoryName
        }).then((response) => {
            let result = response.data
            let code = result.code
            if(code === 0) {
                $message.success("发表成功")
                if(window.history.length >= 3) {
                    $router.go(-1)
                } else {
                    $router.push("/")
                }
            } else {
                $message.error(result.message)
            }
        }).catch(() => {
            $message.error("发表文章出现错误")
        })
    },
    reviseArticle: function (context, data) {
        let $message = data.$message
        let $router = data.$router
        axios.put("/article", {
            id: data.id,
            title: data.title,
            content: data.content,
            categoryName: data.categoryName
        }).then((response) => {
            let result = response.data
            let code = result.code
            if(code === 0) {
                $message.success("修改成功")
                if(window.history.length >= 3) {
                    $router.go(-1)
                } else {
                    $router.push("/")
                }
            } else {
                $message.error(result.message)
            }
        }).catch(() => {
            $message.error("发表文章出现错误")
        })
    }
}

export default actions;
