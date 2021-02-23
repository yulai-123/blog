import axios from "axios";

axios.defaults.baseURL = "http://localhost:8083"
axios.defaults.withCredentials = true
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    if(localStorage.getItem("Auth-Token") !== null) {
        config.headers['Auth-Token'] = localStorage.getItem("Auth-Token")
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
    console.log(response)
    window.a = response
    let result = response.data
    let data = result.data
    if(typeof(data['Auth-Token']) !== 'undefined') {
        localStorage.setItem('Auth-Token', data['Auth-Token'])
    } else {
        localStorage.removeItem('Auth-Token')
    }
    return response;
}, function (error) {
    // 对响应错误做点什么
    return Promise.reject(error);
});

let actions = {
    register: function (context, data) {
        let $message = data.$message
        axios.post('/register', {
            name: data.name,
            passwd: data.passwd
        }).then(function (response) {
            let result = response.data;
            let code = result.code;
            if (code === 0) {
                $message.success("注册成功")
                data.$router.push("/login")
            } else if (code === 101) {
                $message.error("用户名已存在，请重新注册")
            } else if (code === 111) {
                $message.error("用户名或密码不符合要求，请重新填写")
            } else {
                $message.error(result.message)
            }
        }).catch(function () {
            $message.error("注册失败")
        })
    },
    login: function (context, data) {
        let $message = data.$message
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
                $message.error("用户名或密码不符合要求，请重新填写")
            } else {
                $message.error(result.message)
            }
        }).catch(function () {
            $message.error("登录失败")
        })
    },
    logout: function (context, data) {
        let $message = data.$message
        axios.post('/logout').then(function (response) {
            let result = response.data;
            let code = result.code;
            if(code === 0) {
                context.commit("setName", "")
                context.commit("setLoginState", false)
            } else {
                $message.error(result.message)
            }
        }).catch(function () {
            $message.error("退出失败")
        })
    },
    getCategoryList: function (context, data) {
        let $message = data.$message
        axios.get("/categorylist").then((response) => {
            let result = response.data
            let code = result.code
            if(code === 0) {
                let categoryList = result.data.categoryList
                context.commit("setCategoryList", categoryList)
            } else {
                $message.error(result.message)
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
                context.dispatch('getCategoryList', {
                    $message: this.$message
                })
                $message({
                    type: 'success',
                    message: '新增分类：' + data.newCategoryName
                });
            } else {
                $message.error(result.message)
            }
        }).catch(() => {
            this.$message.error("新增分类失败")
        })
    },
    checkLoginState: function (context, data) {
        let $message = data.$message
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
                $message.error(result.message)
            }
        }).catch(() => {
            $message.error("查询登录状态出错")
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
            $message.error("修改文章出现错误")
        })
    }
}

export default actions;
