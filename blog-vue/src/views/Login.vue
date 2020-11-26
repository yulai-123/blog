<template>
    <div class="login-box">
        <div class="login-logo">
            <p><strong>登录</strong></p>
        </div>
        <el-form label-width="80px" :rules="rules" :model="formdata" ref="form">
            <el-form-item label="用户名：" prop="name">
                <el-input v-model="formdata.name"></el-input>
            </el-form-item>
            <el-form-item label="密码：" prop="passwd">
                <el-input type="password" v-model="formdata.passwd"></el-input>
            </el-form-item>
        </el-form>
        <div class="login-button-item">
            <el-button id="login-button" @click.prevent="login">登录</el-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data() {
            let validateName = (rule, value, callback) => {
                if(value === "") {
                    callback(new Error("用户名不能为空"))
                } else if(/^[^\s]+$/.test(value) === false) {
                    callback(new Error("用户名不能包含空白字符"))
                } else if(value.length < 3 || value.length > 16) {
                    callback(new Error("用户名长度应该在3至16个字符之间"))
                } else {
                    callback()
                }
            }
            let validatePasswd = (rule, value, callback) => {
                if(value === "") {
                    callback(new Error("密码不能为空"))
                } else if(/^[^\s]+$/.test(value) === false) {
                    callback(new Error("密码不能包含空白字符"))
                } else if(value.length < 3 || value.length > 16) {
                    callback(new Error("密码长度应该在6至26个字符之间"))
                } else {
                    callback()
                }
            }
            return {
                formdata: {
                    name: "",
                    passwd: ""
                },
                rules: {
                    name: [
                        {validator: validateName, trigger: 'blur'}
                    ],
                    passwd: [
                        {validator: validatePasswd, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            login: function () {
                this.$refs.form.validate((valid) => {
                    console.log(valid)
                    if(valid) {
                        this.$store.dispatch("login", {
                            name: this.formdata.name,
                            passwd: this.formdata.passwd,
                            $router: this.$router
                        })
                    } else {
                        console.log("error")
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .login-box {
        position: absolute;
        width: 300px;
        height: 200px;
        background-color: white;
        margin-top: 150px;
        margin-left: -180px;
        left: 50%;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 1px 1px rgba(161, 159, 159, 0.1);
        /*background: darkgrey;*/
    }
    .login-button-item {
        text-align: center;
    }
    .login-logo {
        text-align: center;
        font-family: "Microsoft YaHei","微软雅黑",serif;
        padding-bottom: 15px;
        color: #000000;
    }
</style>
