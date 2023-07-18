<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% String path  = request.getContextPath(); %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>免费转盘抽奖</title>
    <link rel="stylesheet" type="text/css" href="css/main1.css" />
    <style type="text/css">
        .demo{width:417px; height:417px; position:relative; margin:50px auto}
        #disk{width:417px; height:417px; background:url(image/disk_lottery.jpg) no-repeat}
        #start{width:163px; height:320px; position:absolute; top:46px; left:130px;}
        #start img{cursor:pointer}
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jQueryRotate.2.js"></script>
    <script type="text/javascript" src="js/jquery.easin.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#startbtn").click(function(){
                lottery();
            });
            function lottery(){
                $.ajax({
                    type: 'POST',
                    url: 'lotteryServlet',
                    dataType: 'json',
                    cache: false,
                    error: function(){
                        alert('出错了！');
                        return false;
                    },
                    success:function(json){
                        $("#startbtn").unbind('click').css("cursor","default");
                        var a = json.angle; //角度
                        var p = json.prize; //奖项
                        $("#startbtn").rotate({
                            duration:3000, //转动时间
                            angle: 0,
                            animateTo:1800+a, //转动角度
                            easing: $.easing.easeOutSine,
                            callback: function(){
                                var con = confirm('恭喜你，中得'+p+'\n还要再来一次吗？');
                                if(con){
                                    lottery();
                                }else{
                                    return false;
                                }
                            }
                        });
                    }
                });
            }
        });
    </script>
</head>
<body>
<div id="main">
    <div class="msg"></div>
    <div class="demo">
        <div id="disk"></div>
        <div id="start"><img src="<%=path %>/image/start_lottery.png" id="startbtn"></div>
    </div>
</div>
</body>
</html>