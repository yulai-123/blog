<template>
    <div class="article">
        <el-row>
            <el-col :span="18">
                <span @click="view(article.id)" class="title">
                    {{article.title}}
                </span>
            </el-col>
            <el-col :span="6">
                <el-tag effect="dark" type="info" class="category" color="#409EFF">{{article.category}}</el-tag>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "BlogView",
        props: ['article'],
        computed: {
            ...mapState(['loginState'])
        },
        methods: {
            view(id) {
                this.$router.push("/article/view/" + id)
            },
            getPublishDate(date) {
                return date.substring(0, date.search('T'))
            }
        }
    }
</script>

<style scoped>
    .article {
        border: 1px solid #DCDFE6;
        border-radius: 2px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        margin: 20px 40px;
        padding: 5px 10px;
    }
    .title {
        font-size: 32px;
        color: #606266;
        cursor: pointer;

    }
    .category {
        margin: 10px;
    }
</style>