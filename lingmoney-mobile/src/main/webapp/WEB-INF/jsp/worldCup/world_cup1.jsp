<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
        <title>世界杯有奖竞猜</title>
        <style>
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
                max-width: 7.5rem;
                left: 50%;
                /*background-color: #ffa800;*/
                overflow-y: auto;
                -webkit-overflow-scrolling: touch;
                /*z-index: 9;*/
                transform: translate(-50%, 0);
                -webkit-transform: translate(-50%, 0);
                -moz-transform: translate(-50%, 0);
                -o-transform: translate(-50%, 0);
                transform: translate(-50%, 0);
                background-color: #27449a;
            }
            
            .quiz_award {
                line-height: 0rem;
                font-size: 0rem;
            
                
                
            }
            
            .quiz_award img {
                width: 100%;
                
            }
            .group_all{
                position: absolute;
                width: 6.55rem;
                top: 8rem;
                left: 0.46rem;
                
            }
            .group_stage {
                overflow: hidden;
                margin-bottom: 0.3rem;
            }
            
            .group_stage li {
                width: 1.9rem;
                height: 2.72rem;
                float: left;
                background-color: #dee6ff;
                margin-left: 0.2rem;
                text-align: center;
                position:relative;
            }
            /*国旗*/
            
            .group_stage img {
                width: 0.9rem;
                height: 1.38rem;
            }
            
            .group_stage p {
                width: 100%;
                height: 0.18rem;
                line-height: 0.2rem;
                text-align: center;
                font-size: 0.2rem;
                margin-top: 0.1rem;
            }
            
            .group_stage .nation_name {
                font-size: 0.18rem;
                color: #0c7c41;
                font-weight: 600;
            }
            
            .support_rate {
                color: #051c5c;
                margin-bottom: 0.18rem;
            }
            
            .support_rate span {
                color: #c80000;
            }
            
            .group_stage ul {
                overflow: hidden;
            }
            /*胜平败提交*/
            
            .contest_win {
                width: 1.8rem;
                height: 0.54rem;
                font-size: 0.34rem;
                background-color: #27449a;
                color: #FFFFFF;
                line-height: 0.54rem;
                text-align: center;
                margin-left: 0.05rem;
                cursor: pointer;
                position:absolute;
                bottom:0.05rem;
            }
            
            .contest_flat {
                background-color: #c80000;
            }
            
            
            .quiz_result{
                width:2.8rem;
                height: 0.9rem;
                border-radius: 0.12rem;
                font-size:0.28rem ;
                background-color: #FFFFFF;
                color: #27449a;
                line-height: 0.97rem;
                text-align: center;
                float: left;
                margin-left: 0.2rem;
                margin-top:0.5rem ;
                cursor: pointer;
            }
            .draw_raffle{
                width:2.8rem;
                height: 0.9rem;
                border-radius: 0.12rem;
                font-size:0.28rem ;
                background-color:#38b57b;
                color: #FFFFFF;
                line-height: 0.97rem;
                text-align: center;
                float: right;
                margin-right: 0.2rem;
                margin-top:0.5rem ;
                cursor: pointer;
            }
            .world_Popup{
                width:4.4rem;
                height: 0.8rem;
                border-radius: 0.2rem;
                font-size:0.28rem ;
                background-color: #000000;
                color: #FFF;
                line-height: 0.88rem;
                text-align: center;
                position: absolute;
                top:8.2rem;
                left: 1.65rem;
                display: none;
            }
            /*vs*/
            
            .group_stage .vs_bg {
                background-color: #FFFFFF;
            }
            
            .nation_vs {
                width: 1.9rem;
                height: 2.14rem;
                text-align: center;
            }           
            .nation_vs img {
                width: 1rem;
                height: 1.45rem;
                margin-top: 0.3rem;
            }           
            .count_commit {
                width: 1.9rem;
                height: 0.6rem;
                float: right;           
                font-size: 0.34rem;
                background-color: #C80000;
                color: #FFFFFF;
                line-height: 0.6rem;
                text-align: center;
                margin-top: 0.32rem;
                margin-right: 0.25rem;
                cursor: pointer;
            }
            
            .count_commit1 {
                width: 1.9rem;
                height: 0.6rem;
                float: right;           
                font-size: 0.34rem;
                background-color: #f0f0f0;
                color: #999;
                line-height: 0.6rem;
                text-align: center;
                margin-top: 0.32rem;
                margin-right: 0.25rem;
                cursor: pointer;
            }
            /*倒计时*/
            
            .count_down {
                width: 4rem;
                height: 0.6rem;
                background-color: #38b57b;
                margin-top: 0.32rem;
                margin-left: 0.2rem;
                color: #FFFFFF;
                font-size: 0.28rem;
                line-height: 0.6rem;
                text-align: center;
                float: left;
                cursor: pointer;
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
    z-index: 100;
     opacity: 0;    
   display: none; 
   margin-left:-2.5rem;
   padding:0.2rem 0.1rem;
}   
.huodonggui{
 position:fixed; 
 width:6rem;
 top:7%;
 left:50%;
 margin-left:-3rem;
 display:none;
z-index: 10000;
 
 
}
.huodonggui-box{
  border-radius:0.1rem;
 font-size:0.28rem;
 padding:0.38rem;
}
.huodonggui span{
font-size:0.35rem;
display:block;
text-align: center;
color:#142714;
padding:0.2rem;
}
.huodonggui p{
font-size:0.28rem;
color:#142714;
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
    display:none    
}
.clickhd{width:0.9rem;height:0.9rem;position: absolute; top:0.2rem;right:0.1rem;cursor: pointer;}
        </style>
    </head>

    <body style="position:relative;">

        <div class="main">
            <div class="quiz_award" style="position: relative;">
            <img src="http://static.lingmoney.cn/lingmoneywap/images/24e59fb1-2ee7-4e2a-a264-4bb98b4c228a.jpg" />
            <div class="clickhd"></div>
                <div  class="group_all">
                <div id="worldcup"></div>
                
                  <div class="quiz_result" id="alinkresue">查看竞猜结果</div>
                <a href="/worldCup/world_cup3?token=${token }"> <div class="draw_raffle">立即参与抽奖</div></a>
                </div>               
            </div>
            <div class="world_Popup">您还未参加过竞猜</div>
        </div>
        
        <div class="error_tip"></div>
        <div class="index-Popup-bg"></div>
        <div class="huodonggui">
        <div style=" background:#fff;" class="huodonggui-box">
           <span>活动规则</span>
           <p>
世界杯每场比赛胜负竞猜。<br>
竞猜时间： 6月13日-7月15日。<br>
每场比赛的竞猜截止于该场比赛开始前2小时。<br>
比赛结束的次日10点后可至“查看竞猜结果”查看获奖情况。<br>
猜对一场比赛即可获得一次抽奖机会，抽奖机会可累计。<br>
抽奖100%中奖，奖品为0.1%加息券、0.2%加息券、洗车券、0.3%加息券。<br>

温馨提示：<br>
1.  竞猜只猜胜负，不猜比分。<br>
2.  加息券可用于3月期以上产品，100元起投。<br>
3.  加息券有效期：7天。
             
          </p>
           </div>
           <div style="text-align:center;"><img src="http://static.lingmoney.cn/lingmoneywap/images/8898a2c4-cbfb-488b-8702-ef27ef78eac0.png" class="closed" style="cursor: pointer;"/></div>
        </div>
        <script type="text/javascript" src="/resource/js/jquery-2.1.1.min.js"></script>
        <script>
        
        $(function(){ 
            $('.clickhd').click(function(){
                $('.huodonggui').show()
                $('.index-Popup-bg').show()
            })
            $('.closed').click(function(){
                $('.huodonggui').hide()
                $('.index-Popup-bg').hide()
            })
            
            function formatDate(now) {
                var year = now.getFullYear();
                var month = now.getMonth() + 1;
                var date = now.getDate();
                var hour = now.getHours();
                var minute = now.getMinutes();
                var second = now.getSeconds();
                return year + "/" + getzf(month) + "/" + getzf(date) + "/" + getzf(hour) + ":" + getzf(minute) + ":" + getzf(second);
            }
            function getzf(num) {
                if (parseInt(num) < 10) {
                    num = '0' + num;
                }
                return num;
            }
            /* 比赛场次 */
             $.ajax({
                    url: '/worldCup/queryMatchInfo?token=${token}',
                    type: 'get',
                    dataType: 'json',
                    success: function(data) {
                        var d = data.json;
                        var html=""
                        if(d.code==200){
                            $.each(d.rows,function(i,item){
                                
                                html+='<div class="group_stage" data-matchTime="'+item.matchTime+'" data-gameChoice="'+item.gameChoice+'">'
                                html+='<input type="hidden" id="gameChoice" >'
                                html+='<input type="hidden" id="matchId" value="'+item.id+'">'
                                html+='<ul>'
                                html+='<li><img src="'+item.leftImage+'" /><p class="nation_name">'+item.leftTeam+'</p><p class="support_rate ">支持率 <span>'+item.leftSupportRate+'</span></p><div class="contest_win contest_win1" data-val="2">胜</div></li>'
                                html+='<li class="vs_bg"><div class="nation_vs"><img src="http://static.lingmoney.cn/lingmoneywap/images/289f46c5-5a79-4b5e-9a5f-18c2dae7acf0.png" /><div class="contest_win contest_win2" data-val="1">平</div></li>'
                                html+='<li><img src="'+item.rightImage+'" /><p class="nation_name">'+item.rightTeam+'</p><p class="support_rate ">支持率 <span> '+item.rightSupportRate+'</span></p><div class="contest_win contest_win3" data-val="3">胜</div></li>'
                                html+='</ul>'
                                html+='<div class="count_down"></div>'
                                html+='<div class="count_commit">提交</div>'
                                html+='<div class="count_commit1">提交</div>'
                                html+='</div>'
                                
                            })
                            $('#worldcup').html(html);
                            callbank()
                        }else{
                            
                        }
                    },
                    error: function(d) {}
                });
            
             var callbank=function(){
                 $('.group_stage').each(function(){
                     var _that=$(this)
                     var nowtime1 = Date.parse(new Date())
                     if(nowtime1>$(this).attr('data-matchTime')){
                         $(this).find('.count_down').html('完成')
                         
                     }else if(nowtime1<$(this).attr('data-matchTime')){

                         function countDown(starttime) {
                                setInterval(function() {
                                    var nowtime = Date.parse(new Date())
                                    var time = starttime - nowtime;
                                    if(time==0){
                                         window.location.reload()
                                    }
                                     day = parseInt(time / 1000 / 60 / 60 / 24);
                                     hour = parseInt(time / 1000 / 60 / 60 % 24);
                                     minute = parseInt(time / 1000 / 60 % 60);
                                     seconds = parseInt(time / 1000 % 60);
                                     _that.find('.count_down').html(day + "天" + hour + "小时" + minute + "分钟" + seconds + "秒");
                                }, 1000);

                            }
                         
                          countDown($(this).attr('data-matchTime'));
                     }
                     
                     if($(this).attr('data-gameChoice')==2){
                         $(this).find('.contest_win1').addClass('contest_flat')
                     }else if($(this).attr('data-gameChoice')==1){
                         $(this).find('.contest_win2').addClass('contest_flat')
                     }else if($(this).attr('data-gameChoice')==3){
                         $(this).find('.contest_win3').addClass('contest_flat')
                     }
                     
                     if($(this).attr('data-gameChoice')>0){
                         $(this).find('.count_commit').hide();
                         $(this).find('.count_commit1').show();
                     }else{
                         $(this).find('.count_commit').show();
                         $(this).find('.count_commit1').hide();
                     }
                     
                 })
             }
            
            
             $('body').on('click','.contest_win',function(){
                  $(this).addClass("contest_flat"); 
                  $(this).closest('li').siblings().find('.contest_win').removeClass('contest_flat');
                  $(this).closest('li').closest('ul').closest('.group_stage').find('#gameChoice').val($(this).attr('data-val'))
                  
              })
            /* 提交 */
             $('body').on('click','.count_commit',function(){
                //获取RUL参数值
                    var urlTools = {
                        getUrlParam: function(name) { /*?videoId=identification  */
                            var params = decodeURI(window.location.search); /* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
                            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                            var r = params.substr(1).match(reg);
                            if (r != null) return unescape(r[2]);
                            return null;
                        }
                    };
                    
                    var needLogin = urlTools.getUrlParam("needLogin");
                    var isLogin = urlTools.getUrlParam("isLogin");
                    token = urlTools.getUrlParam("token");

                    //ios Android判断
                 var u = navigator.userAgent;
                    var ua = navigator.userAgent.toLowerCase();
                    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                    if(isiOS){
                        if(needLogin=='N'){
                             window.location.href="/gotoLogin"
                        }else{
                            $.ajax({
                                url: '/worldCup/guessingCompet?token=${token}',
                                type: 'get',
                                data:{
                                    matchId:$(this).closest('.group_stage').find('#matchId').val(),
                                    gameChoice:$(this).closest('.group_stage').find('#gameChoice').val(),   
                                },
                                dataType: 'json',
                                success: function(data) {
                                    var d = data.json;
                                    if(d.code==200){
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
                            }
                        }else if(isAndroid){
                            if(isLogin==1){
                                window.location.href="/gotoLogin"
                            }else{

                                $.ajax({
                                    url: '/worldCup/guessingCompet?token=${token}',
                                    type: 'get',
                                    data:{
                                        matchId:$(this).closest('.group_stage').find('#matchId').val(),
                                        gameChoice:$(this).closest('.group_stage').find('#gameChoice').val(),   
                                    },
                                    dataType: 'json',
                                    success: function(data) {
                                        var d = data.json;
                                        if(d.code==200){
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


                                }
                 
                        }

            })
            
            
            /* 比赛结果列表 */
            
            $('#alinkresue').click(function(){

                //获取RUL参数值
                var urlTools = {
                    getUrlParam: function(name) { /*?videoId=identification  */
                        var params = decodeURI(window.location.search); /* 截取？号后面的部分    index.html?act=doctor,截取后的字符串就是?act=doctor  */
                        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                        var r = params.substr(1).match(reg);
                        if (r != null) return unescape(r[2]);
                        return null;
                    }
                };
                
                var needLogin = urlTools.getUrlParam("needLogin");
                var isLogin = urlTools.getUrlParam("isLogin");
                token = urlTools.getUrlParam("token");

                //ios Android判断
             var u = navigator.userAgent;
                var ua = navigator.userAgent.toLowerCase();
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                if(isiOS){
                    if(needLogin=='N'){
                           window.location.href="/gotoLogin"
                    }else{

                        $.ajax({
                            url: '/worldCup/queryUserGuess?token=${token}',
                            type: 'get',
                            data:{
                                
                            },
                            dataType: 'json',
                            success: function(data) {
                                var d = data.json;
                                if(d.code==200){
                                     window.location.href="/worldCup/world_cup2?token=${token}"
                                }else{
                                    if(!$('.error_tip').is(":animated")){
                                        $('.error_tip').show(100, function() {
                                            $('.error_tip').html('您未参与竞猜');
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
                    }else if(isAndroid){
                        if(isLogin==1){
                               window.location.href="/gotoLogin"
                        }else{
                            $.ajax({
                                url: '/worldCup/queryUserGuess?token=${token}',
                                type: 'get',
                                data:{
                                    
                                },
                                dataType: 'json',
                                success: function(data) {
                                    var d = data.json;
                                    if(d.code==200){
                                         window.location.href="/worldCup/world_cup2?token=${token}"
                                    }else{
                                        if(!$('.error_tip').is(":animated")){
                                            $('.error_tip').show(100, function() {
                                                $('.error_tip').html('您未参与竞猜');
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
             
                    }


                })
            
            
            
            
            
            
            
        }); 
        
            
            

            (function(doc, win) {
                var docEl = doc.documentElement,
                    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                    recalc = function() {
                        var clientWidth = docEl.clientWidth;
                        if(!clientWidth) return;
                        if(clientWidth >= 750) {
                            docEl.style.fontSize = '100px';
                        } else {
                            docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
                        }
                    };
                if(!doc.addEventListener) return;
                win.addEventListener(resizeEvt, recalc, false);
                doc.addEventListener('DOMContentLoaded', recalc, false);
            })(document, window);
            
        </script>
    </body>

</html>
