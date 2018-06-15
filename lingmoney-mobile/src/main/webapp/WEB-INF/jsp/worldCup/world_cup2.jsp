<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
        <title>世界杯 有奖竞猜</title>
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
                overflow-y: auto;
                -webkit-overflow-scrolling: touch;
                transform: translate(-50%, 0);
                -webkit-transform: translate(-50%, 0);
                -moz-transform: translate(-50%, 0);
                -o-transform: translate(-50%, 0);
                transform: translate(-50%, 0);
                
            }           
            .game_result {
                line-height: 0rem;
                font-size: 0rem;
                background: url('http://static.lingmoney.cn/lingmoneywap/images/f2dc1cdf-d449-48ba-8024-218aad26fe44.jpg') repeat top center;
                
                background-size: 100%;
                width: 7.5rem;
                background-color: #27449a;
                padding-bottom: 0.5rem;
            
            }
            
            .game_ball {
                width: 6.18rem;
                height: 2.57rem;
                margin-left: 0.35rem;
            }
            
            .group_stage {
                width: 6.9rem;
                margin: 0 auto;
            }
            
            .group_bgtwo {
                background: url('http://static.lingmoney.cn/lingmoneywap/images/ca9a2426-274d-4ad9-a56c-2103fbead07b.jpg') repeat center center;
                
                overflow: hidden;
            }
            
            .group_bgtwo li {
                width: 1.9rem;
                height: 2.72rem;
                float: left;
                background-color: #dee6ff;
                margin-top: 0.18rem;
                margin-left: 0.3rem;
                text-align: center;
            }
            
            .group_bgtwo img {
                width: 0.9rem;
                height: 1.38rem;
            }
            
            .group_bgtwo p {
                width: 100%;
                height: 0.18rem;
                line-height: 0.2rem;
                text-align: center;
                font-size: 0.2rem;
                margin-top: 0.1rem;
            }
            
            .group_bgtwo .nation_name {
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
            
            .group_bgtwo ul {
                overflow: hidden;
            }
            
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
            }
            
            .contest_flat {
                background-color: #c80000;
            }
            
            .contest_victory {
                background-color: #27449a;
            }
            /*vs*/
            
            .group_bgtwo .vs_bg {
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
                margin-top: 0.32rem;
                margin-right: 0.25rem;
            }
            
            .my_Quiz {
                width: 4.5rem;
                height: 0.86rem;
                margin-top: 0.3rem;
                float: left;
            }
            
            .my_Quiz p {
                color: #FFFFFF;
                font-size: 0.3rem;
                text-align: left;
                text-indent: 0.3rem;
            }
            
            .my_Quiz .correct_time {
                margin-top: 0.25rem;
            }
            
            .quiz_right {
                width: 1.9rem;
                height: 0.8rem;
                border-radius: 0.1rem;
                font-size: 0.34rem;
                background-color: #38b57b;
                color: #FFFFFF;
                line-height: 0.83rem;
                text-align: center;
                float: right;
                margin: 0.3rem 0.28rem 0.2rem 0;
                cursor: pointer;
            }
            
            .division_line {
                width: 6.6rem;
                height: 0.06rem;
                margin: 0 auto;
                margin-top: 0.2rem;
                margin-bottom: 0.2rem;
            }
            
            .division_line img {
                width: 100%;
                height: 100%;
            }
            
            .quiz_fault {
                background-color: #e90000;
            }
            
            .quiz_undeclared {
                background-color: #ff9600;
            }
            .award_number{
                width: 100%;
                height: 0.9rem;
                color: #FFFFFF;
                font-size: 0.3rem;
                background-color: #c80000;
                text-align: center;
                line-height: 0.9rem;
                margin-top: 0.38rem;
                cursor: pointer;
                    bottom: 0rem;
    position: absolute;
                
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
        </style>
    </head>

    <body style="position:relative;background-color: #27449a;">

        <div class="main">          
            <div class="game_result">
        <!--        <div class="group_stage">
                    
                    <img src="http://static.lingmoney.cn/lingmoneywap/images/b67a316b-f154-4f1b-9698-138eb8c562f5.png" class="game_ball" />
                    竞猜正确
                    <div class="group_bgtwo">
                        <ul>
                            <li>
                                <img src="img/guoqi_06.png" />
                                <p class="nation_name">沙特阿拉伯</p>
                                <p class="support_rate ">支持率 <span> 80%</span></p>
                                <div class="contest_win">胜</div>
                            </li>
                            <li class="vs_bg">
                                <div class="nation_vs"><img src="img/vs.png" /></div>
                                <div class="contest_win  contest_flat">平</div>
                            </li>
                            <li>
                                
                                <img src="img/guoqi_06.png" />
                                <p class="nation_name">俄罗斯</p>
                                <p class="support_rate ">支持率 <span> 80%</span></p>
                                <div class="contest_win  contest_victory">胜</div>
                            </li>
                        </ul>
                        <div class="my_Quiz">
                            <p>我的竞猜:( <span>俄罗斯</span> )获胜</p>
                            <p class="correct_time">本场竞猜正确获得1次抽奖机会</p>
                        </div>
                        <div class="quiz_right">竞猜正确</div>
                    </div>
                    <div class="division_line"><img src="http://static.lingmoney.cn/lingmoneywap/images/75f43431-15bd-4d4d-aa74-fe40fe8813a5.png" /> </div>

                </div> -->

                 <div id="worldcup"></div>
                
                <a href="/worldCup/world_cup3?token=${token }"><div class="award_number">可参与抽奖<span class="huojiangcishu">0</span>次</div></a>
            </div>

        </div>
        
<div class="error_tip"></div>
        <script type="text/javascript" src="/resource/js/jquery-2.1.1.min.js"></script>
        <script>
        $(function(){ 
            /* 比赛结果列表 */
             $.ajax({
                    url: '/worldCup/queryUserGuess?token=${token }',
                    type: 'get',
                    data:{
                        
                    },
                    dataType: 'json',
                    success: function(data) {
                        var d = data.json;
                        var html=""
                        if(d.code==200){
                            $.each(d.rows,function(i,item){
                                html+='<div class="group_stage" data-gameChoice="'+item.gameChoice+'" data-leftTeam="'+item.leftTeam+'" data-rightTeam="'+item.rightTeam+'" data-gameResult="'+item.gameResult+'">'
                                html+='<img src="http://static.lingmoney.cn/lingmoneywap/images/b67a316b-f154-4f1b-9698-138eb8c562f5.png" class="game_ball" />'
                                html+='<div class="group_bgtwo">'
                                html+='<ul>'
                                html+='<li><img src="'+item.leftImage+'" /><p class="nation_name">'+item.leftTeam+'</p><p class="support_rate "> <span> </span></p><div class="contest_win contest_win1">胜</div></li>'
                                html+='<li class="vs_bg"><div class="nation_vs"><img src="http://static.lingmoney.cn/lingmoneywap/images/289f46c5-5a79-4b5e-9a5f-18c2dae7acf0.png" /></div><div class="contest_win contest_win2">平</div></li>'
                                html+='<li><img src="'+item.rightImage+'" /><p class="nation_name">'+item.rightTeam+'</p><p class="support_rate "> <span> </span></p><div class="contest_win  contest_win3">胜</div></li>'
                                html+='</ul>'
                                html+='<div class="my_Quiz"><p>我的竞猜:( <span class="huoshengdui"></span> )<b class="pinghide">获胜</b></p><p class="correct_time">本场竞猜正确获得1次抽奖机会</p></div>'
                                html+='<div class="quiz_right">竞猜正确</div>'
                                html+='</div>'
                                html+=' <div class="division_line"><img src="http://static.lingmoney.cn/lingmoneywap/images/75f43431-15bd-4d4d-aa74-fe40fe8813a5.png" /> </div>'
                                html+='</div>'
                                
                                
                                
                            })
                            $('#worldcup').html(html);
                            callbank();
                        }else{
                            
                        }
                    },
                    error: function(d) {}
                });
            
            var callbank=function(){
                $('.group_stage').each(function(){
                    if($(this).attr('data-gameChoice')==2){
                        $(this).find('.huoshengdui').html($(this).attr('data-leftTeam'))
                        $(this).find('.contest_win1').addClass('contest_flat')
                    }else if($(this).attr('data-gameChoice')==3){
                        $(this).find('.huoshengdui').html($(this).attr('data-rightTeam'))
                        $(this).find('.contest_win3').addClass('contest_flat')
                    }else if($(this).attr('data-gameChoice')==1){
                        $(this).find('.huoshengdui').html('平局')
                        $(this).find('.contest_win2').addClass('contest_flat')
                        $(this).find('.pinghide').hide();
                    }
                    
                    if($(this).attr('data-gameChoice')==$(this).attr('data-gameResult')){
                        $(this).find('.quiz_right').html('竞猜正确')
                        $(this).find('.correct_time').html('本场竞猜正确获得1次抽奖机会')
                    }else if($(this).attr('data-gameResult')==0){
                        $(this).find('.quiz_right').html('暂未公布').addClass('quiz_undeclared')
                        $(this).find('.correct_time').html('暂未公布获奖结果')
                    }else{
                        $(this).find('.quiz_right').html('竞猜错误').addClass('quiz_fault')
                        $(this).find('.correct_time').html('本场竞猜错误未获得抽奖机会')
                    }
                    
                })
                
            }
            
            
            
            /* 获奖信息 */
             $.ajax({
                    url: '/worldCup/validateChoujiangCount?token=${token}',
                    type: 'get',
                    data:{
                        typeID:4
                    },
                    dataType: 'json',
                    success: function(data) {
                        var d = data.json;
                        var html=""
                        if(d.code==200){
                            $('.huojiangcishu').html(d.obj)
                        }
                    },
                    error: function(d) {}
                });
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
