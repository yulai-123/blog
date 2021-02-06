import axios from "axios"
import {Message} from "element-ui"

function resolveResponseData(response) {
    let result = response.data
    let code = result.code
    let data = result.data
    if(code === 0) {
        return data.articles
    } else {
        Message.error(result.message)
        return null
    }
}

function getArticleListByPagiantion(that, currentPage) {
    let articles = []
    axios.post("/article/information/list", {
        pageSize: 10,
        position: currentPage
    }).then(
        (response) => {
            articles = resolveResponseData(response)
            that.articles = articles
        }
    ).catch(
        () => {
            Message.error("获取文章列表出现错误")
        }
    )
}
function getAllArticleNumber(that) {
    axios.post("/article/count/all").then(
        (response) => {
            let result = response.data
            let code = result.code
            if(code === 0) {
                let data = result.data
                that.articleSum = data.sum
            } else {
                Message.error(result.message)
            }
        }
    ).catch(
        () => {
            Message.error("获取文章数量错误")
        }
    )
}

function getArticleNumberByCategory(that, categoryId) {
    axios.post("/article/count", {
        id: categoryId
    }).then(
        (response) => {
            let result = response.data
            let code = result.code
            let data = result.data
            if(code === 0) {
                that.articleSum = data.sum
            } else {
                Message.error(result.message)
            }
        }
    ).catch(
        () => {Message.error("获取文章数量错误")}
    )
}

function getArticleListByCategoryAndPagiantion(that, categoryId, currentPage) {
    axios.post("/article/information/list", {
        pageSize: 10,
        position: currentPage,
        categoryId: categoryId
    }).then(
        (response) => {
            that.articles = resolveResponseData(response)
        }
    ).catch(
        () => {Message.error("获取文章列表出现错误")}
    )
}

function getArticleById(that, id) {
    let url = "/article/" + id
    axios.get(url).then((response) => {
        let result = response.data
        let code = result.code
        if(code === 0) {
            let data = result.data
            that.article = data.article
        } else {
            Message.error(result.message)
            if(window.history.length >= 3) {
                that.$router.go(-1)
            } else {
                that.$router.push("/")
            }
        }
    }).catch(() => {
        Message.error("获取博客失败")
    })
}

function deleteArticle(that, articleId) {
    let url = "/article/" + articleId
    axios.delete(url).then((response) => {
        let result = response.data
        let code = result.code
        if(code === 0) {
            Message.success("删除成功")
            if(window.history.length >= 3) {
                that.$router.go(-1)
            } else {
                that.$router.push("/")
            }
        } else {
            Message.error(result.message)
        }
    }).catch(() => {
        Message.error("删除失败")
    })
}

function reviseArticle(that, articleId) {
    let url = "/article/revise/" + articleId
    that.$router.push(url)
}

export {
    getAllArticleNumber,
    getArticleListByPagiantion,
    getArticleListByCategoryAndPagiantion,
    getArticleNumberByCategory,
    getArticleById,
    deleteArticle,
    reviseArticle
}
