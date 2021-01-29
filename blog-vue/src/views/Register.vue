<template>
    <div class="register-box">
        <div class="register-logo">
            <p><strong>注册</strong></p>
        </div>
        <el-form :model="formdata" label-width="100px" :rules="rules" ref="form">
            <el-form-item label="用户名：" prop="name">
                <el-input v-model="formdata.name"></el-input>
            </el-form-item>
            <el-form-item label="密码：" prop="passwd">
                <el-input type="password" v-model="formdata.passwd"></el-input>
            </el-form-item>
            <el-form-item label="确认密码：" prop="repasswd">
                <el-input type="password" v-model="formdata.repasswd"></el-input>
            </el-form-item>
            <div class="register-button-item">
                <el-button id="register-button" @click.prevent="register">注册</el-button>
            </div>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "Register",
        data: function() {
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
            let validateRepasswd = (rule, value, callback) => {
                if(value === "") {
                    callback(new Error("请再次输入密码"))
                } else if(value !== this.formdata.passwd) {
                    callback(new Error("两次输入密码不一样"))
                }
                callback()
            }
            return {
                formdata: {
                    name: "",
                    passwd: "",
                    repasswd: ""
                },
                rules: {
                    name: [
                        {validator: validateName, trigger: 'blur'}
                    ],
                    passwd: [
                        {validator: validatePasswd, trigger: 'blur'}
                    ],
                    repasswd: [
                        {validator: validateRepasswd, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            register: function () {
                this.$refs.form.validate((valid) => {
                    if(valid) {
                        this.$store.dispatch("register", {
                            name: this.formdata.name,
                            passwd: this.formdata.passwd,
                            $router: this.$router,
                            $message: this.$message
                        })
                    } else {
                        this.$message.warning("请认真填写用户名和密码")
                    }
                })
            }
        }

    }
</script>

<style scoped>


    .register-box {
        position: absolute;
        width: 300px;
        height: 250px;
        background-color: white;
        margin-top: 150px;
        margin-left: -180px;
        left: 50%;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 0 1px 1px rgba(161, 159, 159, 0.1);
        /*background: darkgrey;*/
    }
    .register-button-item {
        text-align: center;
    }
    .register-logo {
        text-align: center;
        font-family: "Microsoft YaHei","微软雅黑",serif;
        padding-bottom: 15px;
        color: #000000;
    }
</style>
