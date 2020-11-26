<template>
    <el-container class="write-container" direction="vertical">
        <base-header></base-header>
        <el-main class="write-main">
            <el-row>
                <el-col :offset="20" :span="2"><el-button type="primary" round @click="revise">确认</el-button> </el-col>
                <el-col :span="2"><el-button type="danger" round @click="cancel">取消</el-button></el-col>
            </el-row>
            <div class="write-blog-title">
                <el-input class="write-blog-title-input" type="textarea"
                          :maxlength="30" placeholder="请输入标题..."
                          v-model="blogTitle" resize="none" autosize></el-input>
            </div>
            <mavon-editor v-model="blogContent" class="write-editor"/>
            <div class="write-blog-category">
                <el-form ref="form" :inline="true" :model="formData" :rules="rules">
                    <el-form-item label="分类为：" prop="category">
                        <el-select v-model="formData.category" placeholder="请选择">
                            <el-option
                                    v-for="category in categoryList"
                                    :key="category.id"
                                    :label="category.name"
                                    :value="category.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="open">新增</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-main>
        <base-footer></base-footer>
    </el-container>
</template>

<script>
    import BaseHeader from "../components/BaseHeader";
    import BaseFooter from "../components/BaseFooter";
    import {mapState} from 'vuex'
    import axios from "axios"

    export default {
        name: "Write",
        data() {
            let validateSelectedCategory = (rule, value, callback) => {
                if(value === "") {
                    callback(new Error("请选择分类"))
                } else {
                    callback()
                }
            }
            return {
                blogTitle: "",
                blogContent: "",
                formData: {
                    category: ""
                },
                rules: {
                    category: [
                        {validator: validateSelectedCategory, trigger: 'change'}
                    ]
                }
            }
        },
        components: {
            'base-header': BaseHeader,
            'base-footer': BaseFooter
        },
        computed: {
            ...mapState(['categoryList', 'loginState'])
        },
        methods: {
            revise() {
                if(this.blogTitle === "") {
                    this.$message.error("博客标题不能为空")
                    return;
                }
                if(this.blogContent === "") {
                    this.$message.error("博客内容不能为空")
                    return;
                }
                this.$refs.form.validate((valid) => {
                    if(valid) {
                        this.$store.dispatch("reviseArticle", {
                            id: this.$route.params.id,
                            title: this.blogTitle,
                            content: this.blogContent,
                            categoryName: this.formData.category,
                            $message: this.$message,
                            $router: this.$router
                        })
                    } else {
                        this.$message("请选择分类")
                    }
                })
            },
            cancel() {
                if(window.history.length >= 3) {
                    this.$router.go(-1)
                } else {
                    this.$router.push("/")
                }
            },
            open() {
                window.a = this
                this.$prompt('请输入新的分类名', '', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputValidator: (value) => {
                        if(value === undefined || value === null || value === "") {
                            return "分类名不能为空"
                        } else if(/^[^\s]+$/.test(value) === false) {
                            return "分类名不能包含空白字符"
                        } else if(value.length < 2 || value.length > 10) {
                            return "用户名长度应该在2至10个字符之间"
                        } else {
                            return true
                        }
                    },
                }).then(({ value }) => {
                    this.$store.dispatch("addNewCategory", {
                        newCategoryName: value,
                        "$message": this.$message
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消新增'
                    });
                });
            },
            getArticleById(id) {
                let url = "/article/" + id
                axios.get(url).then((response) => {
                    let result = response.data
                    let code = result.code
                    if(code === 0) {
                        let data = result.data
                        this.blogTitle = data.title
                        this.formData.category = data.categoryName
                        this.blogContent = data.articleContent
                    } else {
                        this.$message.error(result.message)
                    }
                }).catch(() => {
                    this.$message.error("获取博客失败")
                })
            }
        },
        beforeMount() {
            this.$store.dispatch("checkLoginState")
            this.$store.dispatch("getCategoryList")
            this.getArticleById(this.$route.params.id)
        },
        watch: {
            loginState: {
                handler(newValue) {
                    if(newValue === false) {
                        this.$message.warning("此操作需要登录后才可以进行")
                        this.$router.push("/")
                    }
                }
            },
            $route(newValue) {
                this.getArticleById(newValue.params.id)
            }
        }
    }
</script>

<style>
    .write-container {
        background: white;
    }

    .write-main {
        margin: 0 auto;
    }

    .write-blog-title-input textarea {
        margin: 15px auto 0 auto;
        font-size: 32px;
        font-weight: 600;
        /*height: 20px;*/
        border: none;
    }

    .write-editor {
        min-height: 600px !important;
    }

    .write-blog-category {
        margin: 15px 0 0 0;
    }
</style>