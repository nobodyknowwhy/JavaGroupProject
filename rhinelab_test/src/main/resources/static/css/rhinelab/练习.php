<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <h3>PHP数据类型转换 - 显式转换</h3>
    <hr />
    <?php
    $x1 = 10;
    $x2 = '10China10';
    $x3 = 3.14;
    $x4 = 3.98;
    echo '<h4>使用以val结尾的函数</h4>';
    $r1 = floatval($x1);
    $r2 = strval($x1);
    var_dump($x1, $r1, $r2);
    echo '<br/>';
    $r3 = intval($x2);
    $r4 = intval($x3);
    $r5 = intval($x4);
    var_dump($x2, $r3, $r4, $r5);
    echo '<h4>使用强制类型转换运算符</h4>';
    $r6 = (float)$x1;
    var_dump($x1, $r6);
    echo '<br/>';
    $r7 = (bool)$x1;
    var_dump($x1, $r7);
    echo '<br/>';
    $r8 = (object)$x1;
    var_dump($x1, $r8);
    echo '<br/>';
    $r9 = (array)$x1;
    var_dump($x1, $r9);
    echo '<br/>';
    $r10 = (unset)$x1;
    var_dump($x1, $r10);
    ?>
</body>

</html>