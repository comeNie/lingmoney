<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
        <title>世界杯 有奖竞猜</title>
        <link rel="stylesheet" type="text/css" href="/resource/css/layer.css"/>
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
            }
            
            .draw-start {
                line-height: 0rem;
                font-size: 0rem;            
                background: url(http://static.lingmoney.cn/lingmoneywap/images/385bfc46-6513-4864-a25d-5adadbbed102.jpg) no-repeat;
                background-size: 100%;
                width: 7.5rem;
                height: 14.78rem;
            }
            
            .draw_chance {
                width: 3.35rem;
                height: 0.45rem;
                color: #FFFFFF;
                font-size: 0.3rem;
                line-height: 0.45rem;
                text-align: center;
                position: absolute;
                top: 1.1rem;
                left: 2.5rem;
            }
            
            .draw_chance span {
                color: #ff4633;
                font-size: 0.4rem;
            }
            
            .draw {
                position: absolute;
                width: 5.96rem;
                height: 5.2rem;
                top: 2.5rem;
                left: 0.9rem;
            }
            /*抽奖框*/
            
            .draw .item {
                width: 1.8rem;
                height: 1.6rem;
                border-radius: 0.2rem;
                background-color: #74d7a9;
                text-align: center;
            }
            
            .draw .item.active {
                background-color: #02BB00;
            }
            
            .draw .img {
                display: table-cell;
                width: 1.25rem;
                height: 0.7rem;
                vertical-align: middle;
                text-align: center;
                padding-left: 0.25rem;
            }
            
            .draw .img img {
                vertical-align: top;
                width: 100%;
            }
            
            .draw .gap {
                width: 0.05rem;
            }
            
            .draw .gap-2 {
                width: 0.05rem;
            }
            
            .draw .name {
                display: block;
                margin-top: 0.06rem;
                font-size: 0.24rem;
                margin-top: 0.2rem;
            }
            
            .draw .draw-btn {
                display: block;
                height: 1.7rem;
                line-height: 1.7rem;
                border-radius: 0.2rem;
                font-size: 0.25rem;
                font-weight: 700;
                text-decoration: none;
                background-color: #3165fe;
                padding-left: 0.4rem;
            }
            /*奖品弹出*/
            
            #info {
                position: relative;
                text-align: center;
                top: 3rem;
            }
            
            .prize_Pop_big {
                width: 5.15rem;
                height: 5.94rem;
                
            }
            
            .prize_Pop {
                width: 2.34rem;
                height: 1.33rem;
            }
            
            .prize_content {
                position: absolute;
                top: 2.6rem;
                left: 50%;
                text-align: center;
                    margin-left: -2.3rem;
    width: 4.6rem;
            }
            
            .prize_content p {
                font-size: 0.3rem;
                color: #0b2b03;
                text-align: center;
                margin-top: 0.05rem;
                    line-height: 0.45rem;
            }
            
            .prize_content div {
                display: block;
                width: 1.8rem;
                height: 0.6rem;
                color: #FFFFFF;
                font-size: 0.3rem;
                line-height: 0.6rem;
                background-color: #38b57b;
                border-radius: 0.1rem;
                border-style: none;
                cursor: pointer;
                margin-top: 0.1rem;
                margin-left: 1.3rem;
                
            }
            #ios-Android{
               cursor: pointer;
            }
            .prize_img_close {
                width: 0.71rem;
                height: 0.67rem;
                position: absolute;
                top: 6.6rem;
                left: 2.2rem;
                cursor: pointer;
            }
            
            .prize_content a {
                text-decoration: none;
                color: #EA5513;
            }
            /*获取奖品数量*/
            
                .loan_number {
                width: 6.36rem;
                height: 2.24rem;
                position: absolute;
                top: 11.1rem;
                left: 0.55rem;              
            }
            .loan_number li{
                height: 0.55rem;
            }
            .loan_number .loan_text {
                font-size: 0.28rem;
                color: #0b2b03;
                height: 0.55rem;
                width: 2.57rem;
                line-height: 0.55rem;
                text-align: center; 
                float: left;                        
            }
            .loan_number .loan_one {
                font-size: 0.28rem;
                color: #0b2b03;
                height: 0.55rem;
                width: 2.35rem;
                line-height: 0.55rem;
                text-align: center; 
                float: right;                       
            }
.tc-bnk{
   width:4.8rem;
   height:5.5rem;
    position:fixed; 
    top:30%;
    left:50%;
     margin-left:-2.4rem;
     z-index: 10000;
    
      text-align: center;
     
      display:none
   }
   .tc-bnk-1{
        background: #fff;
         padding:0.2rem;
   }
   .tc-bnk p{
       text-align: center;
       font-size:0.24rem;
       color:#666;
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
    
}.error_tip {
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

    <body style="position:relative;">

        <div class="main">
            <div class="draw-start">
                <p class="draw_chance">您还有 <span class="draw_count">5</span> 次抽奖机会</p>

                <div class="draw" id="lottery">
                    <table>
                        <tr>
                            <td class="item lottery-unit lottery-unit-0">
                                <div class="img">                                   
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/d0e5b7ed-2268-4cf7-9fe4-f371a49bdde4.png" />
                                </div>
                                <span class="name">0.1%加息劵</span>
                            </td>
                            <td class="gap"></td>
                            <td class="item lottery-unit lottery-unit-1">
                                <div class="img">       
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/f546b00b-a9ce-4bdc-b620-c49968d6b7a4.png" />
                                </div>
                                <span class="name">洗车卡</span>
                            </td>
                            <td class="gap"></td>
                            <td class="item lottery-unit lottery-unit-2">
                                <div class="img">
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/d0e5b7ed-2268-4cf7-9fe4-f371a49bdde4.png" />                           
                                </div>
                                <span class="name">0.1%加息劵</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="gap-2" colspan="5"></td>
                        </tr>
                        <tr>
                            <td class="item lottery-unit lottery-unit-7">
                                <div class="img">
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/b0842651-372f-47d7-b7ad-18f9701c020e.png" />
                                    
                                </div>
                                <span class="name">0.2%加息劵</span>
                            </td>
                            <td class="gap"></td>
                            <td class="">
                                <a class="draw-btn" href="javascript:">开始抽奖</a>
                            </td>
                            <td class="gap"></td>
                            <td class="item lottery-unit lottery-unit-3">
                                <div class="img">
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/f614f92d-f76b-4920-8780-f828adc83bcf.png" />
                                    
                                </div>
                                <span class="name">0.3%加息劵</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="gap-2" colspan="5"></td>
                        </tr>
                        <tr>
                            <td class="item lottery-unit lottery-unit-6">
                                <div class="img">
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/f614f92d-f76b-4920-8780-f828adc83bcf.png" />
                                    
                                </div>
                                <span class="name">0.3%加息劵</span>
                            </td>
                            <td class="gap"></td>
                            <td class="item lottery-unit lottery-unit-5">
                                <div class="img">
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/f546b00b-a9ce-4bdc-b620-c49968d6b7a4.png" />
                                    
                                </div>
                                <span class="name">洗车卡</span>
                            </td>
                            <td class="gap"></td>
                            <td class="item lottery-unit lottery-unit-4">
                                <div class="img">
                                    <img src="http://static.lingmoney.cn/lingmoneywap/images/b0842651-372f-47d7-b7ad-18f9701c020e.png" />
                                    
                                </div>
                                <span class="name">0.2%加息劵</span>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="loan_number">
                    <ul id="loan-box">
                        <li><p class="loan_text"> </p><p class="loan_one"></p></li>             
                    </ul>
                
                </div>

            </div>
        </div>
<div class="error_tip"></div>
        <!--弹窗-->
        <div id='info' style="display:none;">
            <a href="">
                <img src="http://static.lingmoney.cn/lingmoneywap/images/f288d0cd-6c85-4f33-80a3-af54d5a71da8.png" class="prize_Pop_big" />
                
            </a>
            <div class="prize_content">
                <img src="" class="prize_Pop" />
                <p class="prize_img_name"></p>
                <p class="prize_img_text">抽中奖品可在-我的卡劵-中查看</p>
                <div ><a href="" id="ios-Android" style="color:#fff">立即分享</a></div>
            </div>
            <img src="http://static.lingmoney.cn/lingmoneywap/images/fdcb89f3-a696-46b4-ac7e-5fe77a986374.png" class="prize_img_close" />
        </div>
        
         <div class="tc-bnk">
           <div class="tc-bnk-1">
            <div><img src="http://static.lingmoney.cn/lingmoneywap/images/d2fafef9-4e34-47fd-b9a1-2332032edd31.jpg" style="width:4rem;" /></div>
          <p>世界杯竞猜，扫描二维码</p>
           </div>
          <img src="http://static.lingmoney.cn/lingmoneywap/images/8898a2c4-cbfb-488b-8702-ef27ef78eac0.png" class="closed" style="cursor: pointer;"/>
         </div>
        <div class="index-Popup-bg"></div>
                <script type="text/javascript" src="/resource/js/jquery-2.1.1.min.js"></script>
        <!--弹窗-->
        
        <input type="hidden" value="豪情不减，嬉笑当年——猜球我是认真的！" id="shareTitle">
    <input type="hidden" value="边看球边竞猜，加息券好礼赢不停！" id="shareDescr">
    <input type="hidden" value="http://static.lingmoney.cn/lingqian/2018/9a38e139-5d7d-4c8b-862b-f7a96fb21e4d.png" id="shareImgUrl">
    <input type="hidden" value="http://zldt.lingmoney.cn/extension/world_cup1.html?" id="shareUrl">
        
        <script src="/resource/js/layer.js"></script>
        <script>
            var num;
            var click = "";
            //获取抽奖项
            function choujiang(){
                $.ajax({
                    url: '/worldCup/validateChoujiang?token=${token}&typeID=4',
                    type: 'get',
                    data: {},
                    dataType: 'json',
                    xhrFields: {
                        withCredentials: true
                    },
                    success: function(data) {
                        var d = data.json;
                        if(d.code == 200) {
                            
                            switch(d.obj) {
                                case 31:
                                    num = 1;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/d0e5b7ed-2268-4cf7-9fe4-f371a49bdde4.png');
                                   $(".prize_img_name").html("0.1%加息劵");
                                    break;
                                case 32:
                                    num = 3;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/f546b00b-a9ce-4bdc-b620-c49968d6b7a4.png');
                                    $(".prize_img_name").html("洗车卡");
                                    $(".prize_img_text").html("活动结束后统一寄送");
                                    break;
                                case 33:
                                    num = 2;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/d0e5b7ed-2268-4cf7-9fe4-f371a49bdde4.png');
                                    $(".prize_img_name").html("0.1%加息劵");
                                    break;
                                case 34:
                                    num = 5;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/f614f92d-f76b-4920-8780-f828adc83bcf.png');
                                    $(".prize_img_name").html("0.3%加息劵");
                                    break;
                                case 35:
                                    num = 4;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/b0842651-372f-47d7-b7ad-18f9701c020e.png');
                                    $(".prize_img_name").html("0.2%加息劵");
                                    break;
                                case 36:
                                    num = 5;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/f546b00b-a9ce-4bdc-b620-c49968d6b7a4.png');
                                    $(".prize_img_name").html("洗车卡");
                                    $(".prize_img_text").html("活动结束后统一寄送");
                                    break;
                                case 37:
                                    num = 6;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/f614f92d-f76b-4920-8780-f828adc83bcf.png');
                                    $(".prize_img_name").html("0.3%加息劵");
                                    break;
                                default:
                                   num = 7;
                                    $(".prize_Pop").attr('src', 'http://static.lingmoney.cn/lingmoneywap/images/b0842651-372f-47d7-b7ad-18f9701c020e.png');
                                    $(".prize_img_name").html("0.2%加息劵");
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
            

        
            //          抽奖次数
            $.ajax({
                url: '/worldCup/validateChoujiangCount?token=${token}&typeID=4',
                type: 'get',
                data: {},
                dataType: 'json',
                xhrFields: {
                    withCredentials: true
                },
                success: function(data) {
                    var d = data.json;
                    if(d.code == 200) {
                        $(".draw_count").html(d.obj);
                         click = false; 
                    }else{
                         click = true; 
                        $(".draw_chance").html("你的抽奖次数已经用完")
                    }
                },
                error: function(d) {}
            });
//         抽奖奖品数量
           $.ajax({
                url: '/worldCup/winningCount?token=${token}',
                type: 'get',
                data: {},
                dataType: 'json',
                xhrFields: {
                    withCredentials: true
                },
                success: function(data) {
                    var d = data.json;
                    if(d.code == 200) {
                         var html="";
                         for(var i=0;i<d.rows.length;i++){
                            html+='<li><p class="loan_text"> '+d.rows[i].name+'</p><p class="loan_one">'+d.rows[i].num+'</p></li>'

                         }
                         $('#loan-box').html(html)

                    }
                },
                error: function(d) {}
            });
              


            //          抽奖
            var lottery = {
                index: -1, //当前转动到哪个位置，起点位置
                count: 0, //总共有多少个位置
                timer: 0, //setTimeout的ID，用clearTimeout清除
                speed: 20, //初始转动速度
                times: 0, //转动次数
                cycle: 50, //转动基本次数：即至少需要转动多少次再进入抽奖环节
                prize: -1, //中奖位置
                init: function(id) {
                    if($('#' + id).find('.lottery-unit').length > 0) {
                        $lottery = $('#' + id);
                        $units = $lottery.find('.lottery-unit');
                        this.obj = $lottery;
                        this.count = $units.length;
                        $lottery.find('.lottery-unit.lottery-unit-' + this.index).addClass('active');
                    };
                },
                roll: function() {
                    var index = this.index;
                    var count = this.count;
                    var lottery = this.obj;
                    $(lottery).find('.lottery-unit.lottery-unit-' + index).removeClass('active');
                    index += 1;
                    if(index > count - 1) {
                        index = 0;
                    };
                    $(lottery).find('.lottery-unit.lottery-unit-' + index).addClass('active');
                    this.index = index;
                    return false;
                },
                stop: function(index) {
                    this.prize = index;
                    return false;
                }
            };

            function roll() {
                lottery.times += 1;
                lottery.roll(); //转动过程调用的是lottery的roll方法，这里是第一次调用初始化

                if(lottery.times > lottery.cycle + 10 && lottery.prize == lottery.index) {
                    clearTimeout(lottery.timer);

                    layer.open({
                        type: 1,
                        shadeClose: true,
                        shade: false,
                        maxmin: true, //开启最大化最小化按钮
                        area: ['893px', '600px'],
                        content: $("#info").html()
                    });

                    lottery.prize = -1;
                    lottery.times = 0;
                    click = false;
                } else {
                    if(lottery.times < lottery.cycle) {
                        lottery.speed -= 10;
                    } else if(lottery.times == lottery.cycle) {
                        var index = Math.random() * (lottery.count) | 0; //静态演示，随机产生一个奖品序号，实际需请求接口产生
                        lottery.prize = num;

                    } else {
                        if(lottery.times > lottery.cycle + 10 && ((lottery.prize == 0 && lottery.index == 7) || lottery.prize == lottery.index + 1)) {
                            lottery.speed += 110;
                        } else {
                            lottery.speed += 20;
                        }
                    }
                    if(lottery.speed < 40) {
                        lottery.speed = 40;
                    };
                    lottery.timer = setTimeout(roll, lottery.speed); //循环调用
                }
                return false;
            }

            
            window.onload = function() {
                lottery.init('lottery');
                $('.draw-btn').click(function() {
                    choujiang()
                    if(click) { //click控制一次抽奖过程中不能重复点击抽奖按钮，后面的点击不响应
                        return false;

                    } else {
                        lottery.speed = 100;
                        roll(); //转圈过程不响应click事件，会将click置为false
                        click = true; //一次抽奖完成后，设置click为true，可继续抽奖      
                        return false;
                    }
                });
                //              点击按钮关闭弹出层
                $(document).on("click", ".prize_img_close", function() {
                    layer.closeAll('iframe');
                    window.location.reload()
                });
                
                //ios Android判断
                 var u = navigator.userAgent;
                    var ua = navigator.userAgent.toLowerCase();
                    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                    if(isiOS){
                         $('#ios-Android').attr('href','javascript:void(0)').addClass('iosclick')
                        }else if(isAndroid){
                            $('#ios-Android').attr('href','/recNow').removeClass('iosclick')
 
                        }else{
                             $('#ios-Android').attr('href','javascript:void(0)').addClass('iosclick')
                        }
                    
              $(document).on('click','.iosclick',function(){
                         layer.closeAll('iframe');
                    $('.index-Popup-bg').show();
                    $('.tc-bnk').show(); 
                })
                
                $('.closed').click(function(){
                    $('.index-Popup-bg').hide();
                    $('.tc-bnk').hide();
                    window.location.reload();
                }) 
            };
            
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
            /*fastclick解决移动端点击延迟的问题*/
            //if ('addEventListener' in document) {
            // document.addEventListener('DOMContentLoaded', function() {
            //    FastClick.attach(document.body);
            // }, false);
            //}
        </script>
    </body>

</html>
