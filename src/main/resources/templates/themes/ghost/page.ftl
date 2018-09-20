<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>${post.postTitle} | ${user.userDisplayName}'S Blog</title>
    <meta name="HandheldFriendly" content="True"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="/templates/themes/ghost/sources/casper/assets/built/screen.css"/>
    <meta name="description" content="${post.postSummary?if_exists}"/>
    <link rel="shortcut icon" type="images/x-icon" href="/templates/themes/ghost/sources/upload/2018/4/favicon.png">
    <meta name="referrer" content="no-referrer-when-downgrade"/>
    <meta name="generator" content="hill 0.0.1"/>
    <link rel="alternate" type="application/rss+xml" title="${user.userDisplayName}'S Blog" href="../feed.xml"/>
</head>
<body class="post-template">
<div class="site-wrapper">
<#include "include/siteHeader.ftl">
    <main id="site-main" class="site-main outer">
        <div class="inner">
            <article class="post-full post ">
                <header class="post-full-header">
                    <h1 class="post-full-title">${post.postTitle}</h1>
                </header>
                <figure class="post-full-image" style="background-image: url(${post.postThumbnail})">
                </figure>
                <section class="post-full-content">
                    <div class="post-content">
                    ${post.postContent}
                    </div>
                </section>
                <footer class="post-full-footer">
                    <section class="author-card">
                        <img class="author-profile-image" src="/templates/themes/ghost/sources/static/img/lingm.jpg"
                             alt="灵梦"/>
                        <section class="author-card-content">
                            <h4 class="author-card-name"><a href="/">${user.userDisplayName}</a></h4>
                            <p>${user.userDesc}</p>
                        </section>
                    </section>
                    <div class="post-full-footer-right">
                        <a class="author-card-button" href="/">Read More</a>
                    </div>
                </footer>
                <section class="post-full-comments">
                <#include "include/comment.ftl">
                </section>
            </article>
        </div>
    </main>
    <aside class="read-next outer">
        <div class="inner">
            <div class="read-next-feed">
            <#--上一篇 prePost-->
            <#if beforePost??>
                <article class="post-card post">
                    <a class="post-card-image-link" href="/archives/${beforePost.postUrl}">
                        <div class="post-card-image"
                             style="background-image: url(/static/images/thumbnail/thumbnail-1.jpg)"></div>
                    </a>
                    <div class="post-card-content">
                        <a class="post-card-content-link" href="/archives/${beforePost.postUrl}">
                            <header class="post-card-header">
                                    <span class="post-card-tags">
                                        <#list beforePost.categories as category>
                                        ${category.cateName?if_exists} &nbsp;
                                        </#list>
                                    </span>
                                <h2 class="post-card-title">${beforePost.postTitle}</h2>
                            </header>
                            <section class="post-card-excerpt">
                                <p>${beforePost.postSummary?if_exists}...</p>
                            </section>
                        </a>
                        <footer class="post-card-meta">
                            <ul class="author-list">
                                <li class="author-list-item">
                                    <div class="author-name-tooltip">
                                        do u like ?
                                    </div>
                                    <a href="/" class="static-avatar"><img class="author-profile-image"
                                                                           src="/templates/themes/ghost/sources/static/img/lingm.jpg"
                                                                           alt="灵梦"/></a>
                                </li>
                            </ul>
                            <span class="reading-time">${beforePost.postDate?string("yyyy-MM-dd")}">${beforePost.postDate?string("yyyy-MM-dd")}</span>
                        </footer>
                    </div>
                </article>
            </#if>
            <#--下一篇 nextPost-->
            <#if afterPost??>
                <article class="post-card post">
                    <a class="post-card-image-link" href="/archives/${afterPost.postUrl}">
                        <div class="post-card-image"
                             style="background-image: url(/static/images/thumbnail/thumbnail-1.jpg)"></div>
                    </a>
                    <div class="post-card-content">
                        <a class="post-card-content-link" href="/archives/${afterPost.postUrl}">
                            <header class="post-card-header">
                                    <span class="post-card-tags">
                                        <#list afterPost.categories as category>
                                        ${category.cateName?if_exists} &nbsp;
                                        </#list>
                                    </span>
                                <h2 class="post-card-title">${afterPost.postTitle}</h2>
                            </header>
                            <section class="post-card-excerpt">
                                <p>${afterPost.postSummary?if_exists}...</p>
                            </section>
                        </a>
                        <footer class="post-card-meta">
                            <ul class="author-list">
                                <li class="author-list-item">
                                    <div class="author-name-tooltip">
                                        do u like ?
                                    </div>
                                    <a href="/" class="static-avatar"><img class="author-profile-image"
                                                                           src="/templates/themes/ghost/sources/static/img/lingm.jpg"
                                                                           alt="灵梦"/></a>
                                </li>
                            </ul>
                            <span class="reading-time">${afterPost.postDate?string("yyyy-MM-dd")}">${afterPost.postDate?string("yyyy-MM-dd")}</span>
                        </footer>
                    </div>
                </article>
            </#if>
            </div>
        </div>
    </aside>
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

</body>
</html>
