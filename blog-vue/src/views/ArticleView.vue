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
                    <span class="el-icon-date create-date"> {{getPublishDate}}</span>
                    <span class="author">作者：{{article.author}}</span>
                    <span class="category">分类：{{article.category}}</span>
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
    import {getArticleById, deleteArticle, reviseArticle} from "../util/article"
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
                    publishDate: "",
                    reviseDate: "",
                    content: "",
                    categoryName: "",
                    author: ""
                }
            }
        },
        methods: {
            deleteArticle(){
                deleteArticle(this, this.$route.params.id)
            },
            reviseArticle() {
                reviseArticle(this, this.$route.params.id)
            }
        },
        computed: {
            getPublishDate() {
                return this.article.publishDate.substring(0, this.article.publishDate.search("T"))
            },
            ...mapState(['loginState'])
        },
        beforeMount() {
            getArticleById(this, this.$route.params.id)
        },
        watch: {
            $route(newValue) {
                getArticleById(this, newValue.params.id)
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
