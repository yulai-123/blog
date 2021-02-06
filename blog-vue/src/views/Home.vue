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
                  @current-change="changePage">
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
import {getArticleListByPagiantion, getAllArticleNumber} from "../util/article"

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
      articleSum: 0
    }
  },
  methods: {
    changePage(position) {
      this.currentPage = position
      getArticleListByPagiantion(this, this.currentPage)
    }
  },
  beforeMount() {
    getAllArticleNumber(this)
    getArticleListByPagiantion(this, this.currentPage)
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
