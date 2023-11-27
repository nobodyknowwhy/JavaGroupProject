const express = require('express');
const bodyParser = require('body-parser');
const mysql = require('mysql');

const app = express();
app.use(bodyParser.urlencoded({ extended: false }));

// 创建与数据库的连接
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '12345678',
    database: 'rhinelabtest'
});

// 连接到数据库
connection.connect((err) => {
    if (err) {
        console.error('无法连接到数据库:', err);
        return;
    }
    console.log('已成功连接到数据库');
});

// 处理表单提交
app.post('/submit', (req, res) => {
    const name = req.body.name;
    const email = req.body.email;
    const departments = req.body.department;

    // 将数据插入到数据库的表格中
    const query = "INSERT INTO table_name (name, email, department) VALUES ?";
    const values = departments.map(department => [name, email, department]);

    connection.query(query, [values], (err, result) => {
        if (err) {
            console.error('无法插入数据到数据库:', err);
            res.send('表单提交失败');
            return;
        }

        console.log('已成功插入数据到数据库');
        res.send('表单提交成功');
    });
});

app.listen(3000, () => {
    console.log('服务器已启动，监听端口 3000');
});