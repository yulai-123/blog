<template>
    <el-footer class="footer">
        <p>Designed by
            <strong>
                <router-link to="/" class="footer-link">thesky</router-link>
            </strong>
        </p>
    </el-footer>
</template>

<script>
    export default {
        name: "BaseFooter"
    }
</script>

<style scoped>
    .footer {
        min-width: 100%;
        box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
        margin-top: 100px;
        text-align: center;
        line-height: 60px;
        font-family: 'Open Sans', sans-serif;
        font-size: 18px;
    }
    .footer-link {
        color: #5FB878 !important;
    }
</style>