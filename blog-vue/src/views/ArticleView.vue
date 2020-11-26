<template>
    <div class="container">
        <base-header></base-header>
        <el-row></el-row>

        <el-row class="article">
            <el-col :span="18" :offset="3">
                <el-row>
                    <el-col :span="18">
                        <div>
                            <h1 class="title">{{article.title}}</h1>
                        </div>
                    </el-col>
                    <el-col :span="4" :offset="2" v-if="loginState">
                        <el-button class="article-action-button" type="warning" @click="reviseArticle">修改</el-button>
                        <el-button class="article-action-button" type="danger" @click="deleteArticle">删除</el-button>
                    </el-col>
                </el-row>
                <div class="info">
                    <span class="el-icon-date create-date"> {{getCreateDate}}</span>
                    <span class="author">作者：{{article.author}}</span>
                    <span class="category">分类：{{article.categoryName}}</span>
                </div>
                <mavon-editor  class="content" :toolbarsFlag="false" :subfield="false" defaultOpen="preview" v-model="article.content"></mavon-editor>
            </el-col>
        </el-row>
        <base-footer></base-footer>
    </div>
</template>

<script>
    import BaseHeader from "../components/BaseHeader";
    import BaseFooter from "../components/BaseFooter"
    import axios from "axios"
    import {mapState} from "vuex"

    export default {
        name: "ArticleView",
        components: {
            'base-header': BaseHeader,
            'base-footer': BaseFooter
        },
        data() {
            return {
                article: {
                    id: 0,
                    title: "",
                    createDate: "",
                    reviseDate: "",
                    content: "",
                    categoryName: "",
                    author: ""
                }
            }
        },
        methods: {
            getArticleById(id) {
                let url = "/article/" + id
                axios.get(url).then((response) => {
                    let result = response.data
                    let code = result.code
                    if(code === 0) {
                        let data = result.data
                        this.article.id = parseInt(data.id)
                        this.article.title = data.title
                        this.article.createDate = data.createDate
                        if(data.reviseDate !== null) {
                            this.article.reviseDate = data.reviseDate
                        } else {
                            this.article.reviseDate = null
                        }
                        this.article.categoryName = data.categoryName
                        this.article.content = data.articleContent
                        this.article.author = data.authorName
                    } else {
                        this.$message.error(result.message)
                        if(window.history.length >= 3) {
                            this.$router.go(-1)
                        } else {
                            this.$router.push("/")
                        }
                    }
                }).catch(() => {
                    this.$message.error("获取博客失败")
                })
            },
            deleteArticle() {
                let url = "/article/" + this.$route.params.id
                axios.delete(url).then((response) => {
                    let result = response.data
                    let code = result.code
                    if(code === 0) {
                        this.$message.success("删除成功")
                        if(window.history.length >= 3) {
                            this.$router.go(-1)
                        } else {
                            this.$router.push("/")
                        }
                    } else {
                        this.$message.error(result.message)
                    }
                }).catch(() => {
                    this.$message.error("删除失败")
                })
            },
            reviseArticle() {
                let url = "/article/revise/" + this.$route.params.id
                this.$router.push(url)
            }
        },
        computed: {
            getCreateDate() {
                return this.article.createDate.substring(0, this.article.createDate.search("T"))
            },
            ...mapState(['loginState'])
        },
        beforeMount() {
            this.getArticleById(this.$route.params.id)
        },
        watch: {
            $route(newValue) {
                this.getArticleById(newValue.params.id)
            }
        }
    }
</script>

<style scoped>
    .container {
        background: white;
    }
    .title {
        color: #2a2935;
        margin: 10px 40px;
    }
    .info {
        padding: 10px 0;
        border-bottom: 1px dashed #cacaca;
        border-top: 1px dashed #cacaca;
        margin: 10px 40px;
    }
    .create-date {
        font-size: 15px;
        margin-right: 20px;
        color: #757575;
    }
    .author {
        font-size: 15px;
        color: #757575;
        margin-right: 20px;
    }
    .category {
        font-size: 15px;
        color: #757575;
    }
    .content {
        min-height: 617px;
    }
    .article-action-button {
        margin-top: 20px;
    }
</style>