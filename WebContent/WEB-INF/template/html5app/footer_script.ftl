<script id="tmpl-header" type="text/html">
	<li><a href="${href}" class="ui-btn-active">${title}</a></li>
</script>
<script id="tmpl-footer" type="text/html">
			<li><a  id="navbar-11" href="tel:4008851666" data-icon="minus">电话</a></li>
			<li><a  id="navbar-12" href="sms:13241827675"  data-icon="arrow-d">短信</a></li>
			<li><a  id="navbar-map" href="organiseMap.html" onclick="showmap();" data-icon="info" >地图</a></li>
			<li><a 	id="navbar-share" href="organiseShare.html"  data-icon="forward" >分享</a></li>
            <li><a  id="navbar-msg" href="organiseFeedback.html"  data-icon="grid">留言</a></li>
	
</script>
<script id="tmpl-banner" type="text/html">
 <a href="${href}"><img src="${imgSrc}" title="${imgTitle}" alt="${imgTitle}"/></a>
</script>
<script id="tmpl-companyInfo" type="text/html">
	<li>
		<a href="${href}">
		<span class="list-content">${title}</span>
		</a>
	</li>
</script>

<script id="tmpl-prodInfo" type="text/html">
		                    <li class="${className}">
		                       	<a class="ui-btn" href="${href}" data-theme="c">
		                        <span class="ui-btn-text">
		                        <img width="100%" src="${imgSrc}">
		                        <h3>${title}</h3>
		                        </span>
		                        </a>
		                    </li>
</script>