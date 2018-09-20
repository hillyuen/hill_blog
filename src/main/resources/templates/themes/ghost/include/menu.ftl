<ul class="nav" role="menu">
<@commonTag method="menus">
    <#list menus?sort_by('menuSort') as menu>
        <li role="menuitem">
            <a href="${menu.menuUrl}" target="${menu.menuTarget?if_exists}">${menu.menuName} </a>
        </li>
    </#list>
</@commonTag>
</ul>