<?php 

    //1.连接数据库
    $servername = "localhost";
    $username = "root";
    $password = "Zz15987324860";
    $dbname = "rhinelab";
    
    $db = new mysqli($servername, $username, $password, $dbname);
    
    // Check connection
    if ($db->connect_error) {
      die("Connection failed: " . $db->connect_error);
    }

    //查询数据
    $sql = "
    select CONCAT(YEAR(`生产日期`),'_',quarter(`生产日期`)) qu, 
    '产品名称', sum(`现存数量`) as number, 
    sum(`售量`) as sl, sum(`投入金额`) as t_price, 
    sum(`产品单价`) as price , 
    sum(`售量`) as sl, 
    sum(`投入金额`) as t_price ,
    MAX(`产品单价` * `售量`) as max
    from 产品 
    WHERE `生产日期` BETWEEN '2020-01-01' AND '2025-12-31' group by qu limit 10";
    
    $res=$db->query($sql);
    
    $arr = [];
    foreach($res as $k => $v){
        $arr[$k]['qu'] = $v['qu'];
        $arr[$k]['name'] = $v['name'];
        $arr[$k]['number'] = $v['number'];
        $arr[$k]['sl'] = $v['sl'];
        $arr[$k]['max'] = $v['max'];
        $arr[$k]['t_price'] = $v['t_price'];
        $arr[$k]['t_price'] = $v['t_price'];
        $arr[$k]['lr'] = ($v['price']*$v['number']-$v['t_price']);
    }

    $insert = "INSERT INTO 总结 (季度, 产品, 季度生产数量,季度售量,季度投入本金,季度利润) VALUES ";
    $str = '';
    foreach($arr as $k => $v){
        $str .= ",('".$v['qu']."','".$v['name']."',".$v['number'].",".$v['sl'].",".$v['t_price'].",".($v['price']*$v['number']-$v['t_price']).")";
    }
    $str = ltrim($str, ",");
    $insert .= $str;
    $obj = $db->query($insert);
    if (!$obj) {
        echo "Error: " . $insert . "<br>" . mysqli_error($conn);
        die;
    }
    mysqli_close($db);
?>

<!DOCTYPE html>
<html lang="zh-CN" style="height: 100%">
<head>
    <meta charset="utf-8">
</head>
<body style="height: 100%; margin: 0">
    <div id="container" style="height: 100%"></div>
    <script type="text/javascript" src="https://cdn.bootcdn.net/ajax/libs/echarts/5.1.2/echarts.min.js"></script>
    <script type="text/javascript">
        var dom = document.getElementById('container');
        var myChart = echarts.init(dom, null, {
            renderer: 'canvas',
            useDirtyRect: false
        });
        var arr = <?php echo json_encode($arr); ?>;
        var option;
        var names = [],value = [],number = [],sl = [],max = [];
        for(let i = 0;i<arr.length;i++){
            names[i] = arr[i]['qu']
            value[i] = arr[i]['lr'];
            number[i] = arr[i]['number'];
            sl[i] = arr[i]['sl'];
            max[i] = arr[i]['max'];
        }

        setTimeout(() => {
            option = {
                title: {
                    text: '产品统计'
                },
                tooltip: {
                    trigger: 'axis',
                    axis指向: 'shadow'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    data:names,
                    axisTick: {
                        alignWithLabel: true
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'利润',
                        data:value,
                        type: 'bar'
                    }
                    ,{
                        name:'生产数量',
                        type: 'bar',
                        data: number
                    }
                    ,{
                        name:'售量',
                        type: 'bar',
                        data: sl
                    }
                    ,{
                        name:'最大利润',
                        type: 'bar',
                        data: max
                    }
                ]
            };

            if (option && typeof option === 'object') {
                myChart.setOption(option);
            }

            window.addEventListener('resize', myChart.resize);
        }, "2000");

        
    </script>
</body>
</html>