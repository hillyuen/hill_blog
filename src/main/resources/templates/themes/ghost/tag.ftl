<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>标签：${tag.tagName} | ${user.userDisplayName}'S Blog</title>
    <meta name="HandheldFriendly" content="True"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="/templates/themes/ghost/sources/casper/assets/built/screen.css"/>
    <meta name="description" content="${user.userDisplayName}的学习之路"/>
    <link rel="shortcut icon" type="images/x-icon" href="/templates/themes/ghost/sources/upload/2018/4/favicon.png">
    <meta name="referrer" content="no-referrer-when-downgrade"/>
    <meta name="generator" content="hill 0.0.1"/>
    <link rel="alternate" type="application/rss+xml" title="HillYuen'S Blog" href="../feed.xml"/>
</head>
<body class="tag-template">
<div class="site-wrapper">
    <header class="site-header outer "
            style="background-image: url(/templates/themes/ghost/sources/static/img/home_back.jpg)">
        <div class="inner">
            <#include "include/siteHeaderIndex.ftl">
            <div class="site-header-content">
                <h1 class="site-title">标签：${tag.tagName}</h1>
                <h2 class="site-description">
                    ${tag.posts?size}篇文章
                </h2>
            </div>
        </div>
    </header>
    <main id="site-main" class="site-main outer">
        <div class="inner">
            <div class="post-feed">
            <#if posts??>
                <#list posts.content as post>
                    <#include "include/postBanner.ftl">
                </#list>
            </#if>
            </div>
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
<script>var maxPages = parseInt('2');</script>
<script src="/templates/themes/ghost/sources/casper/assets/js/infinitescroll.js"></script>
</body>
</html>
