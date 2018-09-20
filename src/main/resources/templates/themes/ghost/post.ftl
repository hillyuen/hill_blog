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
                    <section class="post-full-meta">
                        <time class="post-full-meta-date"
                              datetime="${post.postDate?string("yyyy-MM-dd")}">${post.postDate?string("dd")} ${month!" "}, ${post.postDate?string("yyyy")}</time>
                    <#list post.categories as category>
                        <span class="date-divider">/</span> <a
                            href="/categories/${category.cateUrl}">${category.cateName}</a>
                    </#list>
                    </section>
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
                            <h4 class="author-card-name"><a href="/">HillYuen</a></h4>
                            <p>袁训玺的博客</p>
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
                                 style="background-image: url(${beforePost.postThumbnail})"></div>
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
                                <span class="reading-time">${beforePost.postDate?string("yyyy-MM-dd")}</span>
                            </footer>
                        </div>
                    </article>
                </#if>
                <#--下一篇 nextPost-->
                <#if afterPost??>
                    <article class="post-card post">
                        <a class="post-card-image-link" href="/archives/${afterPost.postUrl}">
                            <div class="post-card-image"
                                 style="background-image: url(${afterPost.postThumbnail})"></div>
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
                                <span class="reading-time">${afterPost.postDate?string("yyyy-MM-dd")}</span>
                            </footer>
                        </div>
                    </article>
                </#if>
            </div>
        </div>
    </aside>
    <div class="floating-header">
        <div class="floating-header-logo">
            <a href="/">
                <span>HillYuen'S Blog</span>
            </a>
        </div>
        <span class="floating-header-divider">&mdash;</span>
        <div class="floating-header-title">${post.postTitle}</div>
        <div class="floating-header-share">
            <div class="floating-header-share-label">Share this
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path d="M7.5 15.5V4a1.5 1.5 0 1 1 3 0v4.5h2a1 1 0 0 1 1 1h2a1 1 0 0 1 1 1H18a1.5 1.5 0 0 1 1.5 1.5v3.099c0 .929-.13 1.854-.385 2.748L17.5 23.5h-9c-1.5-2-5.417-8.673-5.417-8.673a1.2 1.2 0 0 1 1.76-1.605L7.5 15.5zm6-6v2m-3-3.5v3.5m6-1v2"/>
                </svg>
            </div>
            <a class="floating-header-share-tw"
               href="#"
               onclick="window.open(this.href, 'share-twitter', 'width=550,height=235');return false;">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
                    <path d="M30.063 7.313c-.813 1.125-1.75 2.125-2.875 2.938v.75c0 1.563-.188 3.125-.688 4.625a15.088 15.088 0 0 1-2.063 4.438c-.875 1.438-2 2.688-3.25 3.813a15.015 15.015 0 0 1-4.625 2.563c-1.813.688-3.75 1-5.75 1-3.25 0-6.188-.875-8.875-2.625.438.063.875.125 1.375.125 2.688 0 5.063-.875 7.188-2.5-1.25 0-2.375-.375-3.375-1.125s-1.688-1.688-2.063-2.875c.438.063.813.125 1.125.125.5 0 1-.063 1.5-.25-1.313-.25-2.438-.938-3.313-1.938a5.673 5.673 0 0 1-1.313-3.688v-.063c.813.438 1.688.688 2.625.688a5.228 5.228 0 0 1-1.875-2c-.5-.875-.688-1.813-.688-2.75 0-1.063.25-2.063.75-2.938 1.438 1.75 3.188 3.188 5.25 4.25s4.313 1.688 6.688 1.813a5.579 5.579 0 0 1 1.5-5.438c1.125-1.125 2.5-1.688 4.125-1.688s3.063.625 4.188 1.813a11.48 11.48 0 0 0 3.688-1.375c-.438 1.375-1.313 2.438-2.563 3.188 1.125-.125 2.188-.438 3.313-.875z"/>
                </svg>
            </a>
            <a class="floating-header-share-fb" href="#"
               onclick="window.open(this.href, 'share-facebook','width=580,height=296');return false;">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32">
                    <path d="M19 6h5V0h-5c-3.86 0-7 3.14-7 7v3H8v6h4v16h6V16h5l1-6h-6V7c0-.542.458-1 1-1z"/>
                </svg>
            </a>
        </div>
        <progress id="reading-progress" class="progress" value="0">
            <div class="progress-container">
                <span class="progress-bar"></span>
            </div>
        </progress>
    </div>
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
<script>$(document).ready(function () {
    var $postContent = $(".post-full-content");
    $postContent.fitVids();
    var progressBar = document.querySelector('#reading-progress');
    var header = document.querySelector('.floating-header');
    var title = document.querySelector('.post-full-title');
    var lastScrollY = window.scrollY;
    var lastWindowHeight = window.innerHeight;
    var lastDocumentHeight = $(document).height();
    var ticking = false;

    function onScroll() {
        lastScrollY = window.scrollY;
        requestTick();
    }

    function onResize() {
        lastWindowHeight = window.innerHeight;
        lastDocumentHeight = $(document).height();
        requestTick();
    }

    function requestTick() {
        if (!ticking) {
            requestAnimationFrame(update);
        }
        ticking = true;
    }

    function update() {
        var trigger = title.getBoundingClientRect().top + window.scrollY;
        var triggerOffset = title.offsetHeight + 35;
        var progressMax = lastDocumentHeight - lastWindowHeight;
        if (lastScrollY >= trigger + triggerOffset) {
            header.classList.add('floating-active');
        } else {
            header.classList.remove('floating-active');
        }
        progressBar.setAttribute('max', progressMax);
        progressBar.setAttribute('value', lastScrollY);
        ticking = false;
    }

    window.addEventListener('scroll', onScroll, {passive: true});
    window.addEventListener('resize', onResize, false);
    update();
});</script>
</body>
</html>
