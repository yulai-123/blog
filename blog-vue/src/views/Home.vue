<template>
  <el-container class="container" direction="vertical">
    <base-header></base-header>
    <el-main>
      <el-row>
        <el-col :offset="6" :span="10">
          <blog-view v-for="article in articles" :article="article" :key="article.id"></blog-view>
        </el-col>
        <el-col :span="8"></el-col>
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
    </el-main>
    <base-footer></base-footer>
  </el-container>
</template>

<script>
import BaseHeader from "../components/BaseHeader";
import BaseFooter from "../components/BaseFooter";
import BlogView from "../components/ArticleInfo"
import axios from 'axios'

export default {
  name: 'Home',
  components: {
    'base-header': BaseHeader,
    'base-footer': BaseFooter,
    'blog-view': BlogView
  },
  data() {
    return {
      articles: [],
      currentPage: 1,
      articleSum: 1000
    }
  },
  methods: {
    getAllArticleInfomation() {
      axios.get("/article/information/all").then(
              (response) => {
                let result = response.data
                let code = result.code
                let data = result.data
                if(code === 0) {
                  this.articles = data.articles
                } else {
                  this.$message.error(result.message)
                }
              }
      ).catch(
              () => {this.$message.error("获取文章出现错误")}
      )
    },
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
                  this.articles = data.articles
                } else {
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
                if(code === 0) {
                  this.articleSum = data.sum
                } else {
                  this.$message.error(result.message)
                }
              }
      ).catch(
              () => {this.$message.error("获取文章数量错误")}
      )
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
  }
  .pagination {
    margin-top: 15px;
  }
</style>
