<#list posts.content as post>
<article class="post-card post">
    <a class="post-card-image-link" href="/archives/${post.postUrl}">
        <div class="post-card-image"
             style="background-image: url(${post.postThumbnail})"></div>
    </a>
    <div class="post-card-content">
        <a class="post-card-content-link" href="/archives/${post.postUrl}">
            <header class="post-card-header">
                                    <span class="post-card-tags">
                                        <#list post.categories as category>
                                        ${category.cateName?if_exists} &nbsp;
                                        </#list>
                                    </span>
                <h2 class="post-card-title">${post.postTitle}</h2>
            </header>
            <section class="post-card-excerpt">
                <p>${post.postSummary?if_exists}...</p>
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
            <span class="reading-time">${post.postDate?string("yyyy-MM-dd")}</span>
        </footer>
    </div>
</article>
</#list>