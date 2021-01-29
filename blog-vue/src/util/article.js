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

function getArticleListByPagiantion(currentPage) {
    let articles = []
    let flag = false
    axios.post("/article/information/list", {
        pageSize: 10,
        position: currentPage
    }).then(
        (response) => {
            articles = resolveResponseData(response)
            flag = 1
        }
    ).catch(
        () => {
            Message.error("获取文章列表出现错误")
        }
    )
    return articles
}
function getAllArticleNumber(that) {
    let sum = 0
    axios.post("/article/count/all").then(
        (response) => {
            let result = response.data
            let code = result.code
            let data = result.data
            if(code === 0) {
                that.sum = data.sum
            } else {
                Message.error(result.message)
            }
        }
    ).catch(
        () => {
            Message.error("获取文章数量错误")
        }
    )
    return sum
}

export {
    getAllArticleNumber,
    getArticleListByPagiantion
}
