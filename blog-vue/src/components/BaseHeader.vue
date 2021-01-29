<template>
    <el-header class="header">
        <el-row class="header-row">
            <el-col :span="5">
                <router-link to="/" class="header-logo">
                    <img src="../assets/img/thesky.png" class="header-logo-img" alt="thesky">
                </router-link>
            </el-col>
            <el-col :span="13">
                <el-menu :default-active="activeIndex" mode="horizontal" text-color="#303133" active-text-color="#409EFF" :router="true">
                    <el-menu-item index="/" class="el-icon-s-home">首页</el-menu-item>
                    <el-menu-item index="/category" class="el-icon-s-grid">博客分类</el-menu-item>
                    <el-menu-item index="/reorganize" class="el-icon-s-order">博客归档</el-menu-item>
                    <el-menu-item v-if="loginState" index="/write" class="el-icon-edit">写博客</el-menu-item>
                </el-menu>
            </el-col>
            <el-col :span="6">
                <el-menu mode="horizontal" text-color="#303133" default-active="/" :router="true">
                    <template v-if="loginState">
                        <el-submenu index="2">
                            <template v-slot:title>{{username}}</template>
                            <template v-slot:default>
                                <el-menu-item><el-button type="text" @click="logout">退出</el-button></el-menu-item>
                            </template>
                        </el-submenu>
                    </template>
                    <template v-else>
                        <el-menu-item index="/login">登录</el-menu-item>
                        <el-menu-item index="/register">注册</el-menu-item>
                    </template>
                </el-menu>
            </el-col>
        </el-row>
    </el-header>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "BaseHeader",
        data() {
            return {
                logoImage: require("../assets/img/logo.jpg"),
                logoFit: "none"
            }
        },
        computed: {
            ...mapState(["loginState", "username"]),
            activeIndex: function () {
                return this.$route.path
            }
        },
        methods: {
            logout: function () {
                this.$store.dispatch("logout", {
                    $message: this.$message
                })
            }
        },
        beforeMount() {
            this.$store.dispatch("checkLoginState", {
                $message: this.$message
            })
        }
    }
</script>

<style scoped>
    .header {
        min-width: 100%;
        box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    }

    .header-logo {
    }

    .header-logo-img {
        padding-top: 7px;
        padding-left: 50px;
        max-height: 2.4em;
    }
</style>
