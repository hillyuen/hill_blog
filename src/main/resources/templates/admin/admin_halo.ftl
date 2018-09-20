<#compress >
<#include "module/_macro.ftl">
<@head title="关于Halo"></@head>
<div class="wrapper">
    <!-- 顶部栏模块 -->
    <#include "module/_header.ftl">
    <!-- 菜单栏模块 -->
    <#include "module/_sidebar.ftl">
    <div class="content-wrapper">
        <style type='text/css'>
            blockquote{
                border-left: 4px solid #dddddd;
                padding: 0 15px;
                color: #777777;
                font-size: 16px;
            }
        </style>
        <section class="content-header">
            <h1 style="display: inline-block;">关于Blog</h1>
            <ol class="breadcrumb">
                <li>
                    <a data-pjax="true" href="/admin"><i class="fa fa-dashboard"></i> 首页</a>
                </li>
                <li class="active">关于Blog</li>
            </ol>
        </section>
        <section class="content container-fluid">
            <div id='write' class='is-mac'>
            </div>
        </section>
    </div>
    <#include "module/_footer.ftl">
</div>
<@footer></@footer>
</#compress>
