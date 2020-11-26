package top.thesky341.blogapi;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.thesky341.blogapi.controller.ArticleController;
import top.thesky341.blogapi.dto.ArticleDto;
import top.thesky341.blogapi.entity.Admin;
import top.thesky341.blogapi.entity.Article;
import top.thesky341.blogapi.entity.ArticleContent;
import top.thesky341.blogapi.entity.Category;
import top.thesky341.blogapi.service.ArticleService;

import javax.annotation.Resource;

@SpringBootTest
public class ArticleTest {
    @Resource
    org.apache.shiro.mgt.SecurityManager securityManager;

    public void init() throws Exception {
        ThreadContext.bind(securityManager);
    }

    @Resource
    ArticleService articleService;

    @Test
    public void addArticleInBulk() {
        ThreadContext.bind(securityManager);

        ArticleDto articleDto = new ArticleDto();
        articleDto.setCategoryName("JavaWeb");
        articleDto.setTitle("sprngboot自动装配的原理");
        articleDto.setContent("1. ### ` String s = new String(\"abc\")` 这段代码创建了几个对象？      两个\n" +
                "\n" +
                "   + 这种题目主要就是为了考察程序员对字符串对象的常量池掌握与否。上述的语句中是创建了2个对象，第一个对象是”abc”字符串存储在常量池中，第二个对象在JAVA Heap中的 String 对象。 \n" +
                "   + 通过字面量方式创建的字符串会放在String Pool，使用new 创建的字符串则是放在堆里面。\n" +
                "   + 字符串常量池（String Pool）保存着所有字符串字面量（literal strings），这些字面量在编译时期就确定。不仅如此，还可以使用 String 的 intern() 方法在运行过程将字符串添加到 String Pool 中\n" +
                "   + 在 Java 7 之前，String Pool 被放在运行时常量池中，它属于永久代。而在 Java 7，String Pool 被移到堆中。这是因为永久代的空间有限，在大量使用字符串的场景下会导致 OutOfMemoryError 错误\n" +
                "   + [深入解析String#intern](https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html)\n" +
                "\n" +
                "2. ### 浅拷贝和深拷贝\n" +
                "\n" +
                "   + 浅拷贝是对对象内部的基本数据类型进行了值传递，对引用类型进行了引用传递般的拷贝，实际只生成了一个对象\n" +
                "   + 深拷贝理论上应该使得拷贝结果与原先的对象不再有关联，这就要求我们不仅要对拷贝对象内部引用变量进行拷贝，也要对引用变量内部的引用变量进行拷贝，也就是需要进行`\"递归\"拷贝` , 最终结果和序列化相似。另外，有些\"深拷贝\"实现实际上只对拷贝对象内部的引用变量进行了浅拷贝，而没有继续\"递归\"拷贝.\n" +
                "\n" +
                "3. 在java中，会使用缓冲池来保存一些常用的java基本类型的包装类型对象\n" +
                "\n" +
                "   1. 基本类型对应的缓冲池如下：\n" +
                "      + boolean values true and false\n" +
                "      + all byte values\n" +
                "      + short values between -128 and 127\n" +
                "      + int values between -128 and 127\n" +
                "      + char in the range \\u0000 to \\u007F\n" +
                "   2. 在使用这些基本类型对应的包装类型时，如果该数值范围在缓冲池范围内，就可以直接使用缓冲池中的对象\n" +
                "   3. new Integer(123) 与 Integer.valueOf(123) 的区别在于：\n" +
                "      - new Integer(123) 每次都会新建一个对象；\n" +
                "      - Integer.valueOf(123) 会使用缓存池中的对象，多次调用会取得同一个对象的引用。\n" +
                "   4. 在 jdk 1.8 所有的数值类缓冲池中，Integer 的缓冲池 IntegerCache 很特殊，这个缓冲池的下界是 - 128，上界默认是 127，但是这个上界是可调的，在启动 jvm 的时候，通过 -XX:AutoBoxCacheMax=<size>  来指定这个缓冲池的大小，该选项在 JVM 初始化的时候会设定一个名为 java.lang.IntegerCache.high 系统属性，然后  IntegerCache 初始化的时候就会读取该系统属性来决定上界\n" +
                "\n" +
                "4. ");
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(articleDto.getContent());
        Category category = new Category();
        category.setName(articleDto.getCategoryName());
        Admin admin = new Admin();
//        admin.setName();
        admin.setName("thesky1");
        articleService.addNewArticle(article, articleContent,
                category, admin);
        for (int i = 0; i < 1000; i++) {
            articleService.addNewArticle(article, articleContent,
                    category, admin);
        }
    }
}
