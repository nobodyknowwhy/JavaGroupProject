<!DOCTYPE html>
<html>

<head>
    <title>APPLICATION APPROVAL</title>
    <link rel='stylesheet' type='text/css' href='product_buying.css'>
    <link rel="icon" type="image/jpeg" href="./rhinelab/rahinelab_logo.png" sizes="32x32">
</head>

<body>

    <?php
    require_once "../html/includes/class.phpmailer.php";
    require_once "../html/includes/class.smtp.php";
    $servername = "localhost";
    $username = "root";
    $password = 'Zz15987324860';
    $dbname = "rhinelab";
    $conn = mysqli_connect($servername, $username, $password, $dbname);
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $order_id = $_POST['order-id'];
        $product_id = $_POST['product-id'];
        $purchase_quantity = $_POST['purchase-quantity'];
        if (isset($_POST['approve'])) {
            $sql2 = "UPDATE 产品 SET `现存数量` = `现存数量` - '$purchase_quantity'  WHERE `产品编号` = '$product_id';";
            if (mysqli_query($conn, $sql2)) {
            } else {
                echo "更新记录时出现错误：" . mysqli_error($conn);
            }
            $sql3 = "UPDATE 购买 SET 完成状态='已完成' WHERE 订单编号 = '$order_id';";

            if (mysqli_query($conn, $sql3)) {
            } else {
                echo "更新记录时出现错误：" . mysqli_error($conn);
            }
        } 
    }
    $sql = "SELECT * FROM 购买, 产品 WHERE 购买.`产品编号` = 产品.`产品编号` and 完成状态='待发货';";
    $result = mysqli_query($conn, $sql);
    if (mysqli_num_rows($result) > 0) {
        echo "<table><tr><th>Order ID</th><th>Product ID</th><th>Product Name</th><th>Product Quantity</th><th>Purchase Quantity</th><th>Purchase Price</th><th>Purchase Time</th></tr>";
        while ($row = mysqli_fetch_assoc($result)) {
            echo "<tr><td>" . $row["订单编号"] . "</td><td>" . $row["产品编号"] . "</td><td>" . $row["产品名称"] . "</td><td>" . $row["现存数量"] . "</td><td>" . $row["购买数量"] . "</td><td>" . $row["购买总价"] . "</td><td>" . $row["购买日期"] . "</td>";
            echo "<td><form method='post'><input type='hidden' name='order-id' value='" . $row["订单编号"] . "'>";
            echo "<input type='hidden' name='product-id' value='" . $row["产品编号"] . "'>";
            echo "<input type='hidden' name='purchase-quantity' value='" . $row["购买数量"] . "'>";
            echo "<button type='submit' name='approve'>DELIVER</button></form></td></tr>";
        }
        echo "</table>";
    } else {
        echo "<h1>Nothing Recorded</h1>";
    }
    mysqli_close($conn);
    ?>

</body>

</html>