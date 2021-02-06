<template>
    <el-container class="write-container" direction="vertical">
        <base-header></base-header>
        <el-main class="write-main">
            <el-row>
                <el-col :offset="20" :span="2"><el-button type="primary" round @click="publish">发布</el-button> </el-col>
                <el-col :span="2"><el-button type="danger" round @click="cancel">取消</el-button></el-col>
            </el-row>
            <div class="write-blog-title">
                <el-input class="write-blog-title-input" type="textarea"
                          :maxlength="30" placeholder="请输入标题..."
                          v-model="article.title" resize="none" autosize></el-input>
            </div>
            <mavon-editor v-model="article.content" class="write-editor"/>
            <div class="write-blog-category">
                <el-form ref="article" :inline="true" :model="article" :rules="rules">
                    <el-form-item label="分类为：" prop="category">
                        <el-select v-model="article.category" placeholder="请选择">
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
                article: {
                    title: "",
                    content: "",
                    cateogry: ""
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
            publish() {
                if(this.article.title === "") {
                    this.$message.error("博客标题不能为空")
                    return;
                }
                if(this.article.content === "") {
                    this.$message.error("博客内容不能为空")
                    return;
                }
                this.$refs.article.validate((valid) => {
                    if(valid) {
                        this.$store.dispatch("addNewArticle", {
                            title: this.article.title,
                            content: this.article.content,
                            categoryName: this.article.category,
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
            }
        },
        beforeMount() {
            this.$store.dispatch("checkLoginState", {
                $message: this.$message
            })
            this.$store.dispatch("getCategoryList", {
                $message: this.$message
            })
        },
        watch: {
            loginState: {
                handler(newValue) {
                    if(newValue === false) {
                        this.$message.warning("此操作需要登录后才可以进行")
                        this.$router.push("/")
                    }
                }
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
