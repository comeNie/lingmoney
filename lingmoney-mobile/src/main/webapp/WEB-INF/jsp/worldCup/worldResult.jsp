<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport"
    content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>领钱儿-测测你的“伪球迷”指数</title>
<style type="text/css">
/*em设置*/
            
html {
    font-size: 100%;
}

ul,ol,li,p {
    list-style: none;
    margin: 0px;
    padding: 0px;
}

html,body,.screen {
    position: relative;
    width: 100%;
    height: 100%;
    margin: 0 auto;
}

.main {
    position: absolute;
    width: 100%;
    max-width: 6.4rem;
    left: 50%;
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
    transform: translate(-50%, 0);
    -webkit-transform: translate(-50%, 0);
    -moz-transform: translate(-50%, 0);
    -o-transform: translate(-50%, 0);
    transform: translate(-50%, 0);
}

.world-cup {
    line-height: 0rem;
    font-size: 0rem;
}

.world-cup img {
    width: 100%;            
}           
.begin_testing a {
    position: absolute;
    width: 3.53rem;
    height: 0.59rem;
    top: 9.4rem;
    left: 1.4rem;
    background-size: 100%;
    color: #FFFFFF;
    font-size: 0.25rem;
    text-align: center;
    line-height: 0.6rem;
    text-decoration: none;
}
.world-cup-tip{width:5.36rem;height:1.51rem;position:absolute;top:3rem;left:0.4rem;font-size:0.55rem;color:#27731b;text-align:center;line-height:0.65rem;}
.world-cup-tip span{font-size:0.28rem;display:block;}
  .begin-tc {position:fixed; 
    top: 30%; 
    width: 6.31rem;
    font-size: .24rem;
   text-align: center;
   color: #fff;
   left: 50%; 
   z-index: 10000;
   display: none; 
   margin-left:-3.15rem;
  }  
  .begin-tc-tip{
       background: url('http://static.lingmoney.cn/lingmoneywap/images/ea2faa75-7c71-4758-91ad-767921210a38.png') no-repeat top center;background-size:100%;
       width:6.31rem;
       height:4.09rem;
  
  }
  .index-Popup-bg {
    width: 100%;
    height: 100%;
    position: fixed;
    opacity: 0.5;
    background: #000;
    z-index: 9999;
    left: 0;
    top: 0;
    display:none;
    
}
.begin-tc-red{
 width:5.34rem;
 height:0.1rem;
 font-size:0.48rem;
 color:#fff;
 text-align:center;
 padding-top: 2.3rem;
}
 .begin-huod{
    width: 1rem;
    height: 0.3rem;
    position: absolute;
    left: 50%;
    margin-left: -0.5rem;
    bottom: 0.37rem
}   
.shu-iphone {
    width: 5.68rem;
    height: 4.69rem;
    background:
        url('http://static.lingmoney.cn/lingmoneywap/images/5eecf08a-d5a0-4671-861a-28dcdd6fb7c7.png')
        no-repeat top center;
    background-size: 100%;
    position: fixed;
    z-index: 10000;
    top: 20%;
    left: 50%;
    margin-left: -2.84rem;
    display: none;
}



.shu-iphone ul {
    width: 3.38rem;
    display: block;
    margin-left: auto;
    margin-right: auto;
    padding-top: 1.3rem;
}

.shu-iphone ul li {
    line-height: 0.6rem;
    height: 0.6rem;
    margin-top: 0.3rem;
    position: relative;
    s
}

.shu-iphone ul li input {
    width: 3.38rem;
    height: 0.6rem;
    background: #e4e6e9;
    border: solid #c5c8cc 1px;
    vertical-align: top;
    font-size:0.25rem;
    -webkit-appearance: none;
    
}
.error_tip {
    position:fixed; 
    top: 50%; 
    /* height: 0.68rem; */
    width: 5rem;
    font-size: .24rem;
    /*  line-height: .68rem; */
    border-radius: 3px;
    background: rgba(0,0,0,0.5);
     text-align: center;
      color: #fff;
     left: 50%; 
  /*  transform: translate(-50%,-50%); */
    z-index: 100000;
     opacity: 0;    
   display: none; 
   margin-left:-2.5rem;
   padding:0.2rem 0.1rem;
}
.jtfx{
   position:fixed; 
   opacity: 0.6;
	background: #000;
	color:#fff;
	height:0.88rem;
	line-height:0.88rem;
    top: 0%; 
    left: 50%; 
    width:7.5rem;
    margin-left:-3.75rem;
    font-size:0.3rem;
    text-align: center;
     display: none; 
}
</style>
</head>
<body>
    <div class="screen">
        <div class="main">
            <div class="world-cup">
              <img src="http://static.lingmoney.cn/lingmoneywap/images/db7ee7f6-6bb9-4536-8847-9f6da5b8e1af.jpg" />
                <div class="world-cup-tip">
                <b  class="tipss"></b><span class="tips"></span>
                </div>
        
                <div class="begin_testing">
                    <a href="javascript:void(0)"  >
                        <img src="http://static.lingmoney.cn/lingmoneywap/images/0522ca87-4bbc-4bc9-a812-9baf450975f2.png" />
                    </a>
                </div>
                    <div  class="begin-huod"></div>
            </div>
        </div>
        <div class="error_tip"></div>
        <!-- 红包 -->
        <div class="begin-tc">
           <div class="begin-tc-tip">
              <div class="begin-tc-red">20元复投红包</div>
           </div>
            <div style="width:0.34rem;margin-left:auto; margin-right:auto;margin-left:45%;">    <img src="http://static.lingmoney.cn/lingmoneywap/images/8898a2c4-cbfb-488b-8702-ef27ef78eac0.png" style="width:0.34rem;" class="closed"/></div>    
        </div>
        <!-- 红包 end-->
        <div class="index-Popup-bg" ></div>
        <!-- 手机号输入 -->
        <div class="shu-iphone">
            <ul>
                <li><input type="text" placeholder="请输入手机号码" class="text-iphone"></li>
                <!-- <li><input type="text" placeholder="请输入验证码" style="width:1.72rem;">
                <span class="iphone-tip">获取验证码</span>
                <span class="iphone-tips">获取验证码</span>
                
                </li> -->
                <li><input type="button" value="提交"
                    style="height: 0.7rem; background: #e1491d; color: #fff; border: solid #ef7654 1px;margin-top:0.3rem" class="tjbuttom"></li>
            </ul>
        </div>
        <!-- 手机号输入 end-->
        <input type="hidden" class="fhFZzhi">
        
            <!-- 活动规则 -->
        <div class="huod-tip" style="width:5.52rem;height:7.33rem;display:none; position:fixed; z-index:10000; top:10%;left:50%;margin-left:-2.76rem; background: url('http://static.lingmoney.cn/lingmoneywap/images/a3af3481-8c6e-4a98-a8ca-fb7ee8975a7e.png') no-repeat top center;background-size:100%;">
            <p style="font-size: 0.24rem;padding-top: 1.5rem;width: 4.2rem;margin: 0 auto;height: 5.8rem;color: #666;line-height: 0.48rem;">
                1.  本测试一共10题，题目随机。<br>
                2.  答题完成后直接显示结果并获得奖励。<br>
                3.  领取现金红包奖励需输入手机号验证联系方式。<br>
                4.  红包为复投红包，第一次投资及新手标皆不可用。<br>
                5.  投资红包规则：6月期及以上产品，投资金额2万元以上；<br>
                6.  红包有效期限：有效期7天；
            </p>
            <div style="text-align:center;"><img src="http://static.lingmoney.cn/lingmoneywap/images/8898a2c4-cbfb-488b-8702-ef27ef78eac0.png" class="closed"/></div>
        </div>
        <!-- 活动规则 -->
    </div>

    <div id="jsondata"></div>
    <div class="jtfx">截图保存,即可分享</div>
    <script type="text/javascript" src="/resource/js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="/resource/js/worldCupcommon.js"></script>
    <script>
    (function(doc, win) {
    	
    	if(!$('.jtfx').is(":animated")){
			$('.jtfx').show(100, function() {
				$('.jtfx').animate({
					opacity: 0.8,
					
					},1000, function() {
					window.setTimeout(function(){
						$('.jtfx').animate({
							opacity: 0,
							},
							1000, function() {
							$('.jtfx').hide();
						});
					}, 3000)
				});
			});		
		  } 
        $('.begin-huod').click(function(){
             $('.index-Popup-bg').show();
             $('.huod-tip').show();
        })
      $('.closed').click(function(){
         $('.index-Popup-bg').hide();
         $('.huod-tip').hide();
          $('.begin-tc').hide();
      })
      
        
      var urlTools = {
            getUrlParam: function(name) { /*?videoId=identification  */
                var params = decodeURI(window.location.search); /* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = params.substr(1).match(reg);
                if (r != null) return unescape(r[2]);
                return null;
            }
        };

        var tips =urlTools.getUrlParam("tips");
        var score=urlTools.getUrlParam("score");
        $('.fhFZzhi').val(score);
        
        

        

        var index = tips .lastIndexOf("—");  
        str  = tips .substring(index + 1, tips .length);
        $('.tips').html(str);
        
        var strr=tips.split('—')[0]
        $('.tipss').html(strr);
        
        
        
        
        /* 红包领取 */
        $('.begin_testing img').click(function(){
            $('.shu-iphone').show();
            $('.index-Popup-bg').show();
        })
        
        
        
        
         /* 提交人数手机号 */
            $('.tjbuttom').click(function(){
                if($('.text-iphone').val()==''){
                    if(!$('.error_tip').is(":animated")){
                        $('.error_tip').show(100, function() {
                            $('.error_tip').html('手机号不能为空');
                            $('.error_tip').animate({
                                opacity: 1,
                                },1000, function() {
                                window.setTimeout(function(){
                                    $('.error_tip').animate({
                                        opacity: 0,
                                        },
                                        1000, function() {
                                        $('.error_tip').hide();
                                    });
                                }, 2000)
                            });
                        });     
                      } 
                    
                }else{
                     $.ajax({
                            url: '/worldCup/saveScoreByPhone',
                            type: 'get',
                            data: {
                                phone :$('.text-iphone').val(),
                                score:$('.fhFZzhi').val()
                            }, 
                            success: function(data) {
                                var d = data.json;
                                if(d.code==200){
                                     //领取
                                	$.ajax({
                                        url: '/worldCup/receiveRedEnve',
                                        type: 'get',
                                        data: {
                                        	token :"${token}"
                                        }, 
                                        success: function(data) {
                                            var d = data.json;
                                            if(d.code==200){
                                                $('.begin-tc').show()
                                                $('.index-Popup-bg').show();
                                                $('.begin-tc-red').html(d.msg);
                                                $('.shu-iphone').hide();
                                                $('.fenshuzhi').val(d.obj.score);
                                                $('.fenshutip').val(d.obj.scoreMsg);
                                                window.location.href="/worldCup/worldResult?tips="+$('.fenshutip').val()+"&score="+$('.fenshuzhi').val()
                                            }else{
                                            	if(!$('.error_tip').is(":animated")){
                                                    $('.error_tip').show(100, function() {
                                                        $('.error_tip').html(d.msg);
                                                        $('.error_tip').animate({
                                                            opacity: 1,
                                                            },1000, function() {
                                                            window.setTimeout(function(){
                                                                $('.error_tip').animate({
                                                                    opacity: 0,
                                                                    },
                                                                    1000, function() {
                                                                    $('.error_tip').hide();
                                                                });
                                                            }, 2000)
                                                        });
                                                    });     
                                                  } 
                                            }
                                        },
                                        error: function(d) {}
                                    }); 
                                }else {
                                   
                                    if(!$('.error_tip').is(":animated")){
                                        $('.error_tip').show(100, function() {
                                            $('.error_tip').html(d.msg);
                                            $('.error_tip').animate({
                                                opacity: 1,
                                                },1000, function() {
                                                window.setTimeout(function(){
                                                    $('.error_tip').animate({
                                                        opacity: 0,
                                                        },
                                                        1000, function() {
                                                        $('.error_tip').hide();
                                                    });
                                                }, 2000)
                                            });
                                        });     
                                      } 
                                }
                            },
                            error: function(d) {}
                        }); 
                    
                }
                
                
            })
        
        
        
        
        
        
      
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function() {
                var clientWidth = docEl.clientWidth;
                if(!clientWidth) return;
                if(clientWidth >= 640) {
                    docEl.style.fontSize = '100px';
                } else {
                    docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
                }
            };
        if(!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
    
</script>
        
</script>
</body>
</html>