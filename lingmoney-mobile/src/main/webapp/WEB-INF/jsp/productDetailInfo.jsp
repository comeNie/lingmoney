<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<!-- <meta name="viewport" content="width=device-width,initial-scale=1"> -->
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<title>项目详情</title>
<!-- <meta charset="utf-8" /> -->
<meta name="viewport" content="width=device-width,initial-scale=1" />
<!-- <link rel="stylesheet" href="/resource/css/index.css"> -->
<!-- <script type="text/javascript" src="/resource/js/jquery-1.8.3.min.js"></script> -->
<link rel="stylesheet" href="/resource/css/project.css">
<script src="/resource/js/jquery-1.8.3.min.js"></script>
<script src="/resource/js/project.js"></script>
<script src="/resource/js/tabbedContent.js"></script>
<script src="/resource/js/jquery.mobile-1.0a4.1.min.js"></script>
<link rel="stylesheet" href="/resource/js/demm/css/demo.css"> 
<link rel="stylesheet prefetch" href="/resource/js/demm/css/photoswipe.css">
<link rel="stylesheet prefetch" href="/resource/js/demm/css/default-skin/default-skin.css">
</head>
<body style="background:#f9f9f9">
<input id="pid" value="${pid }" type="hidden">

	<section class="clear">
        <div class="tabbed_content">
			<div class="tabs">
			<div class="moving_bg">&nbsp;</div>
				<span class="tab_item tab_item1">项目介绍</span>
				<span class="tab_item tab_item2">借款人信息</span>
				<span class="tab_item tab_item3">常见问题</span>
				
			</div>
			
			<div class="slide_content">						
				<div class="tabslider" >
					<div style="font-size:0rem;line-height:0rem;">&nbsp;</div>
					   <ul class="Project_details">
                         <div class="tebs-box" style="padding-bottom:0.24rem;">
                            <p class="project-tip">产品详情</p>
                            <div class="" id="productDetailInfo">
                                <p class="project-box-pro">
                                    <span class="project-left">产品名称</span>
                                    <span class="project-right" id = "productName"></span>
                                </p>
                                <p class="project-box-pro">
                                    <span class="project-left">产品期限</span>
                                    <span class="project-right" id = "productTimeLimit" ></span>
                                </p>
                                <p class="project-box-pro">
                                    <span class="project-left">认购规模</span>
                                    <span class="project-right" id = "productScale"></span>
                                </p>
                                <p class="project-box-pro">
                                    <span class="project-left">起投递增</span>
                                    <span class="project-right" id = "productStartIncrease"></span>
                                </p>
                                <p class="project-box-pro">
                                    <span class="project-left">产品起息日</span>
                                    <span class="project-right">募集结束计划成立之日</span>
                                </p>
                                <p class="project-box-pro">
                                    <span class="project-left">预计到期日</span>
                                    <span class="project-right" id = "productEndDt"></span>
                                </p>
                                <p class="project-box-pro">
                                    <span class="project-left">预期年化利率</span>
                                    <span class="project-right" id = "productFyield"></span>
                                </p>
                            </div>  
                         </div>
                         
                         <div class="tebs-box">
                            <p class="project-tip">审核状态</p>
                            <div class="">
                              <img src="/resource/images/img.png" style="width:7.02rem"/>
                            </div>  
                         </div>
                         
                         <div class="tebs-box">
                            <p class="project-tip project-jilv">投资记录 <img src="/resource/images/rightarrow.png"/></p>
                         </div>
                       </ul>	
                       
                       <ul class="borrower">
                            <div class="tebs-box" id = "productBorrowerInfo">
	                             
	                         </div>
	                         
	                         <div class="tebs-box" id="diyawu">
	                            <p class="project-tip">抵押物图片</p>
	                            <div class="mortgages">
	                               			  <!-- 抵押物图片 -->
							   	<div id="noneimg-img"></div>
									<div id="_contain">
										<div class="banner" style="position: relative;">
											<!-- <div class="top_bg"></div> -->
											
										</div>

										<div class="contain">
											
												
													<div class="body" style="width:100%">
														
														<div class="text">
															<div class="title"></div>
															<div class="txt"></div>
															
															<div class="my-gallery" data-pswp-uid="2">
														<!-- 	图片循环 -->
															 <figure>
																<div><a href="resource/images/1.png" data-size="286x220"  class="dataalink" style="background: url(resource/images/1.png) no-repeat;background-size: 100%;  width:3.39rem;display:block;height:2.54rem" ><img style="height:100%;" src="resource/images/1.png" class="dataimg"></a></div>
															</figure>
															
															
															<!-- 	图片循环end -->
															
															</div>
															
															
														</div>
													</div>
												
												
											
										</div>

									</div>
							
							 <!-- 抵押物图片 -->
	                            </div>  
	                         </div>
                       </ul>	
                       
                       <ul class="common_problem">
                       
                       </ul>			   
					
					</div>
				<br style="clear:both" />
			</div>
			
		</div>
		
		
	
	</section>
	<script src="/resource/js/demm/js/photoswipe.js"></script>
<script src="/resource/js/demm/js/photoswipe-ui-default.min.js"></script>		
<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

    <!-- Background of PhotoSwipe. 
         It's a separate element as animating opacity is faster than rgba(). -->
    <div class="pswp__bg"></div>

    <!-- Slides wrapper with overflow:hidden. -->
    <div class="pswp__scroll-wrap">

        <!-- Container that holds slides. 
            PhotoSwipe keeps only 3 of them in the DOM to save memory.
            Don't modify these 3 pswp__item elements, data is added later on. -->
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>

        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
        <div class="pswp__ui pswp__ui--hidden">

            <div class="pswp__top-bar">

                <!--  Controls are self-explanatory. Order can be changed. -->

                <div class="pswp__counter"></div>

                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>

                <button class="pswp__button pswp__button--share" title="Share" style="display:none;"></button>

                <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>

                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

                <!-- element will get class pswp__preloader--active when preloader is running -->
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                      <div class="pswp__preloader__cut">
                        <div class="pswp__preloader__donut"></div>
                      </div>
                    </div>
                </div>
            </div>

            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                <div class="pswp__share-tooltip"></div> 
            </div>

            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
            </button>

            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
            </button>

            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>

        </div>

    </div>

</div>	
<div class="allimg" style="display:none"></div>	
<script type="text/javascript">

function addDate(date, days) {
    if(days == undefined || days == '') {
        days = 1;
    }
    var date = new Date(date);
    date.setDate(date.getDate() + days);
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var mm = "'" + month + "'";
    var dd = "'" + day + "'";

    if(mm.length == 3) {
        month = "0" + month;
    }
    if(dd.length == 3) {
        day = "0" + day;
    }

    var time = date.getFullYear() + "-" + month + "-" + day
    return time;
}

$(document).ready(function () {  
    var u = navigator.userAgent;
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    // alert(isiOS);
    if(isiOS){
        $('.project-jilv').hide();
    } else{
        $('.project-jilv').show();
    }

	$.ajax({
		url:'/product/productDetailInfoData?pId=' + $('#pid').val(),
	    type:'get',
	    success: function(data) {
	    	if(data.code == 200) {
	    		var fTime = data.obj.timeLimit.replace('天','');
	    		$('#productName').text(data.obj.name);
	    		$('#productTimeLimit').text(data.obj.timeLimit);
	    		$('#productScale').text(data.obj.scale);
	    		$('#productStartIncrease').text(data.obj.startIncrease);
	    		$('#productEndDt').text(addDate(data.obj.endDt,parseInt(fTime)));
	    		$('#productFyield').text(data.obj.fYield);
	    		
	    		$('#productDetailInfo').append(data.obj.description);
	    		if (data.obj.introduction == null && data.obj.lonnerInfo == null) {
	    		    //在这隐藏借款人信息
	    		    $('.tab_item2').remove();
	    		    $('.tab_item').css('width','50%') 
	    		    $('.moving_bg').css('width','50%') 
	    		    $('.borrower').remove();
	    		    
	    		    
	    		} else {
	    			/* alert(data.obj.introduction) */
	    			$('.allimg').html(data.obj.introduction); 
	                $('#productBorrowerInfo').html(data.obj.lonnerInfo);
	                if(data.obj.introduction==null){
					     $('#diyawu').hide()
					}else{
					    $('#diyawu').show()
					}
	                var html="";
	                	$.each($('.allimg >img'), function(i, item){  
	                	html+="<figure>";
	    				html+='<div><a href="'+$(this).attr('src')+'" data-size="286x220"  class="dataalink" style="display:block"><img src="'+$(this).attr('src')+'"></a></div>'	
	    		        html+="</figure>" 
	                })
	             
	    		       $('.my-gallery').html(html)
	    		       
	    		       setTimeout(function(){
	    		    	   $('.dataalink').each(function(){
	 	                	  var imglink=$(this).find('img').attr('src');
	 	                	  $(this).attr('href',imglink)
	 	                	 /*  $(this).css('background',url(imglink)) */
	 	                  })
	    		    	   
	    		       },100)  
	    		}
	    		
	    		if (data.obj.normalProblem == null) {
	    			//在这隐藏常见问题
	    			
	    			    $('.tab_item3').remove();
		    		    $('.tab_item').css('width','50%') 
		    		     $('.moving_bg').css('width','50%') 
		    		    $('.common_problem').remove();
		    		    
	    			
	    		} else {
	    			$('.common_problem').html(data.obj.normalProblem);
	    		}
	    		
	    		
	    		if(data.obj.introduction == null && data.obj.lonnerInfo == null && data.obj.normalProblem == null){
	    			    $('.tab_item3').remove();
	    			    $('.tab_item2').remove();
		    		   /*  $('.tab_item').css('width','100%')  */
		    		    /*  $('.moving_bg').css('width','100%')  */
		    		    $('.common_problem').remove();
		    		    $('.borrower').remove();
		    		    $('.tabs').hide();
		    		    $('.slide_content').css('top','0rem')
		    		    $('.tebs-box').css({'margin-top':'0rem','margin-bottom':'0.24rem'})
	    		}
	    		
	    		
	    		
	    	} else {
	    		 //这里做数据为空处理 
	    	}
	    }
	})
	
	
});  
</script>
<script>
(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if(clientWidth>=750){
                docEl.style.fontSize = '100px';
            }else{
                docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);   
</script>
<script type="text/javascript">
	var initPhotoSwipeFromDOM = function(gallerySelector) {

    // 解析来自DOM元素幻灯片数据（URL，标题，大小...）
    // (children of gallerySelector)
    var parseThumbnailElements = function(el) {
        var thumbElements = el.childNodes,
            numNodes = thumbElements.length,
            items = [],
            figureEl,
            linkEl,
            size,
            item,
			divEl;

        for(var i = 0; i < numNodes; i++) {

            figureEl = thumbElements[i]; // <figure> element

            // 仅包括元素节点
            if(figureEl.nodeType !== 1) {
                continue;
            }
			divEl = figureEl.children[0];
            linkEl = divEl.children[0]; // <a> element
			
            size = linkEl.getAttribute('data-size').split('x');

            // 创建幻灯片对象
            item = {
                src: linkEl.getAttribute('href'),
                w: parseInt(size[0], 10),
                h: parseInt(size[1], 10)
            };



            if(figureEl.children.length > 1) {
                // <figcaption> content
                item.title = figureEl.children[1].innerHTML; 
            }

            if(linkEl.children.length > 0) {
                // <img> 缩略图节点, 检索缩略图网址
                item.msrc = linkEl.children[0].getAttribute('src');
            } 

            item.el = figureEl; // 保存链接元素 for getThumbBoundsFn
            items.push(item);
        }

        return items;
    };

    // 查找最近的父节点
    var closest = function closest(el, fn) {
        return el && ( fn(el) ? el : closest(el.parentNode, fn) );
    };

    // 当用户点击缩略图触发
    var onThumbnailsClick = function(e) {
        e = e || window.event;
        e.preventDefault ? e.preventDefault() : e.returnValue = false;

        var eTarget = e.target || e.srcElement;

        // find root element of slide
        var clickedListItem = closest(eTarget, function(el) {
            return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
        });

        if(!clickedListItem) {
            return;
        }

        // find index of clicked item by looping through all child nodes
        // alternatively, you may define index via data- attribute
        var clickedGallery = clickedListItem.parentNode,
            childNodes = clickedListItem.parentNode.childNodes,
            numChildNodes = childNodes.length,
            nodeIndex = 0,
            index;

        for (var i = 0; i < numChildNodes; i++) {
            if(childNodes[i].nodeType !== 1) { 
                continue; 
            }

            if(childNodes[i] === clickedListItem) {
                index = nodeIndex;
                break;
            }
            nodeIndex++;
        }



        if(index >= 0) {
            // open PhotoSwipe if valid index found
            openPhotoSwipe( index, clickedGallery );
        }
        return false;
    };

    // parse picture index and gallery index from URL (#&pid=1&gid=2)
    var photoswipeParseHash = function() {
        var hash = window.location.hash.substring(1),
        params = {};

        if(hash.length < 5) {
            return params;
        }

        var vars = hash.split('&');
        for (var i = 0; i < vars.length; i++) {
            if(!vars[i]) {
                continue;
            }
            var pair = vars[i].split('=');  
            if(pair.length < 2) {
                continue;
            }           
            params[pair[0]] = pair[1];
        }

        if(params.gid) {
            params.gid = parseInt(params.gid, 10);
        }

        return params;
    };

    var openPhotoSwipe = function(index, galleryElement, disableAnimation, fromURL) {
        var pswpElement = document.querySelectorAll('.pswp')[0],
            gallery,
            options,
            items;

        items = parseThumbnailElements(galleryElement);

        // 这里可以定义参数
        options = {
          barsSize: { 
            top: 100,
            bottom: 100
          }, 
		   fullscreenEl : false,
			shareButtons: [
			{id:'wechat', label:'分享微信', url:'#'},
			{id:'weibo', label:'新浪微博', url:'#'},
			{id:'download', label:'保存图片', url:'{{raw_image_url}}', download:true}
			],

            // define gallery index (for URL)
            galleryUID: galleryElement.getAttribute('data-pswp-uid'),

            getThumbBoundsFn: function(index) {
                // See Options -> getThumbBoundsFn section of documentation for more info
                var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
                    pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
                    rect = thumbnail.getBoundingClientRect(); 

                return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
            }

        };

        // PhotoSwipe opened from URL
        if(fromURL) {
            if(options.galleryPIDs) {
                // parse real index when custom PIDs are used 
                for(var j = 0; j < items.length; j++) {
                    if(items[j].pid == index) {
                        options.index = j;
                        break;
                    }
                }
            } else {
                // in URL indexes start from 1
                options.index = parseInt(index, 10) - 1;
            }
        } else {
            options.index = parseInt(index, 10);
        }

        // exit if index not found
        if( isNaN(options.index) ) {
            return;
        }

        if(disableAnimation) {
            options.showAnimationDuration = 0;
        }

        // Pass data to PhotoSwipe and initialize it
        gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
        gallery.init();
    };

    // loop through all gallery elements and bind events
    var galleryElements = document.querySelectorAll( gallerySelector );

    for(var i = 0, l = galleryElements.length; i < l; i++) {
        galleryElements[i].setAttribute('data-pswp-uid', i+1);
        galleryElements[i].onclick = onThumbnailsClick;
    }

    // Parse URL and open gallery if it contains #&pid=3&gid=1
    var hashData = photoswipeParseHash();
    if(hashData.pid && hashData.gid) {
        openPhotoSwipe( hashData.pid ,  galleryElements[ hashData.gid - 1 ], true, true );
    }
	};

	// execute above function
	
	initPhotoSwipeFromDOM('.my-gallery');

	
	$(".my-gallery>figure>div").each(function(){
		$(this).css('height','2.54rem');
	});
	function more(obj,id) {
 		if ($('#txt'+id).is(":hidden")) {
 			$('#p'+id).hide();
 	 		$('#txt'+id).show();
 	 		obj.innerHTML='收起';
 		} else {
 			$('#p'+id).show();
 	 		$('#txt'+id).hide();
 	 		obj.innerHTML='全文';
 		}
 	}
</script>
</body>
</html>
