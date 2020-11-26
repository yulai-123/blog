<template>
    <el-container class="container" direction="vertical">
        <base-header></base-header>
        <el-row >
            <el-col :offset="4">
                <el-timeline class="timeline">
                    <el-timeline-item
                            v-for="article in articles"
                            :key="article.id"
                            size="large"
                            type="primary"
                            :timestamp="getSuitableDate(article.publishDate)">
                        <span @click="view(article.id)" class="title">
                            {{article.title}}
                        </span>
                    </el-timeline-item>
                </el-timeline>
            </el-col>
        </el-row>
        <el-row>
            <el-col :offset="8" :span="10">
                <el-pagination
                        background
                        layout="prev, pager, next"
                        :total="articleSum" class="pagination"
                        @current-change="getArticleListByPagiantion">
                </el-pagination>
            </el-col>
        </el-row>
        <base-footer></base-footer>
    </el-container>
</template>

<script>
    import BaseFooter from "../components/BaseFooter"
    import BaseHeader from "../components/BaseHeader"
    import axios from "axios"

    export default {
        name: "Reorganize",
        data() {
            return {
                articles: [],
                currentPage: 1,
                articleSum: 1000
            }
        },
        components: {
            'base-header': BaseHeader,
            'base-footer': BaseFooter
        },
        methods: {
            getArticleListByPagiantion(position) {
                this.currentPage = position
                axios.post("/article/information/list", {
                    pageSize: 10,
                    position: this.currentPage
                }).then(
                    (response) => {
                        let result = response.data
                        let code = result.code
                        let data = result.data
                        if(code === 0) {
                            this.articles = data.articles} else {
                            this.$message.error(result.message)
                        }
                    }
                ).catch(
                    () => {this.$message.error("获取文章出现错误")}
                )
            },
            getAllArticleNumber() {
                axios.post("/article/count/all").then(
                    (response) => {
                        let result = response.data
                        let code = result.code
                        let data = result.data
                        if (code === 0) {
                            this.articleSum = data.sum
                        } else {
                            this.$message.error(result.message)
                        }
                    }
                ).catch(
                    () => {
                        this.$message.error("获取文章数量错误")
                    }
                )
            },
            getSuitableDate(date) {
                return date.substring(0, date.search("T"))
            },
            view(id) {
                this.$router.push("/article/view/" + id)
            }
        },
        beforeMount() {
            window.a = this
            this.getAllArticleNumber();
            this.getArticleListByPagiantion(this.currentPage);
        }
    }
</script>

<style scoped>
    .container {
        background: white;
        min-height: 980px;
    }
    .timeline {
        margin-top: 50px;
        margin-bottom: 30px;
    }
    .title {
        cursor: pointer;
    }
</style>