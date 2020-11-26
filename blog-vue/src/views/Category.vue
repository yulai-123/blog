<template>
    <el-container class="container" direction="vertical">
        <base-header></base-header>
        <el-main>
            <el-row>
                <el-col :offset="3" :span="18">
                    <el-row>
                        <el-col v-for="category in categoryList" :key="category.id"
                                :span="5" :offset="1" class="card-style">
                            <div @click="viewCategory(category.id)"><el-card >{{category.name}}</el-card></div>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-main>
        <base-footer></base-footer>
    </el-container>
</template>

<script>
    import BaseHeader from "../components/BaseHeader";
    import BaseFooter from "../components/BaseFooter";
    import {mapState} from "vuex"

    export default {
        name: "Category",
        components: {BaseHeader, BaseFooter},
        comments: {
            'base-header': BaseHeader,
            'base-footer': BaseFooter
        },
        data() {
            return {

            }
        },
        computed: {
            ...mapState(['categoryList'])
        },
        beforeMount() {
            this.$store.dispatch("getCategoryList")
            window.a = this
        },
        methods: {
            viewCategory(id) {
                let url = "/category/view/" + id
                // console.log(url)
                this.$router.push(url)
            }
        }
    }
</script>

<style scoped>
    .container {
        background: white;
        min-height: 980px;
    }
    .category {
        background: #409EFF;
    }
    .card-style {
        margin-top: 30px;
        cursor: pointer;
    }
</style>