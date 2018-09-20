<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>文章归档 | ${user.userDisplayName}'S Blog</title>
    <meta name="HandheldFriendly" content="True"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="ghost/sources/casper/assets/built/screen.css"/>
    <meta name="description" content="${user.userDisplayName}的学习之路"/>
    <link rel="shortcut icon" type="images/x-icon" href="ghost/sources/upload/2018/4/favicon.png">
    <meta name="referrer" content="no-referrer-when-downgrade"/>
    <meta name="generator" content="hill 0.0.1"/>
    <link rel="alternate" type="application/rss+xml" title="${user.userDisplayName}'S Blog" href="feed.xml"/>
</head>
<body class="page-template">
<div class="site-wrapper">
    <#include "include/siteHeader.ftl">
    <main id="site-main" class="site-main outer">
        <div class="inner">
            <article class="post-full post page ">
                <header class="post-full-header">
                    <h1 class="post-full-title">文章归档</h1>
                </header>
                <figure class="post-full-image"
                        style="background-image: url(/templates/themes/ghost/sources/upload/2018/6/YYnote设计师推荐壁纸20180623153309505.jpg)">
                </figure>
                <section class="post-full-content">
                    <div class="post-content">
                        <h1>分类</h1>
                        <ul>
                        <#if categories??>
                        <#list categories as category>
                            <li>
                                <a href="/categories/${category.cateUrl}">${category.cateName}(${category.posts?size})</a>
                            </li>
                        </#list>
                        </#if>
                        </ul>
                        <h1>标签</h1>
                        <ul>
                        <#if tags??>
                        <#list tags as tag>
                            <li>
                                <a href="/tags/${tag.tagUrl}">${tag.tagName}(${tag.posts?size})</a>
                            </li>
                        </#list>
                        </#if>
                        </ul>
                        <h1>归档</h1>
                        <#if archives??>
                        <#list archives as archive>
                            <h2>${archive.year}年${archive.month}月</h2>
                            <ul class="listing">
                            <#list archive.posts as post>
                                <li>
                                ${post.postDate?string('MM月dd日')}：<a href="/archives/${post.postUrl}">${post.postTitle}</a>
                                </li>
                            </#list>
                            </ul>
                        </#list>
                        </#if>
                    </div>
                </section>
            </article>
        </div>
    </main>
<#include "include/footer.ftl">
</div>
<script>var images = document.querySelectorAll('.kg-gallery-image img');
images.forEach(function (image) {
    var container = image.closest('.kg-gallery-image');
    var width = image.attributes.width.value;
    var height = image.attributes.height.value;
    var ratio = width / height;
    container.style.flex = ratio + ' 1 0%';
})
</script>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous">
</script>
<script type="text/javascript" src="/templates/themes/ghost/sources/casper/assets/js/jquery.fitvids.js"></script>
<script>$(function () {
    var $postContent = $(".post-full-content");
    $postContent.fitVids();
});</script>
</body>
</html>
