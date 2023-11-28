import random

import pymysql #导入模块
import os

import requests
from bs4 import BeautifulSoup
from lxml import html
etree = html.etree

#本机数据库连接
db = pymysql.connect(host='localhost', port=3306, user='root', password='12345678', db='rhinelab', charset='utf8')


#使用cursor()方法获取操作游标
cursor = db.cursor()

url = "https://wiki.biligame.com/arknights/%E5%B9%B2%E5%91%98%E6%95%B0%E6%8D%AE%E8%A1%A8"
resp = requests.get(url)
resp.encoding = "utf-8"
main_page = BeautifulSoup(resp.text, "html.parser")
a_list = main_page.find_all("tr", attrs={"class": "divsort"})

bianhao = 2023001
url_list = []

for a in a_list:
    div = a.find("div", attrs={"class": "floatnone"})
    urls = "https://wiki.biligame.com"+div.find("a").get("href")
    url_list.append(urls)

for i in url_list:
    resps = requests.get(i)
    resps.encoding = "utf-8"
    main_pages = BeautifulSoup(resps.text, "html.parser")

    # name1 = main_pages.find('h1', attrs={"class": "firstHeading", "id": "firstHeading"}).get_text()
    et = etree.HTML(resps.text)
    # 姓名
    name = "".join(et.xpath('//h1[@id="firstHeading"]/text()'))
    # 性别
    xinbie = "".join(et.xpath('//*[@id="mw-content-text"]/div/div[3]/div/div[2]/div/div[2]/table/tbody/tr[2]/td[2]/text()')).strip()
    # 照片
    pic = name+".jpg"
    # 民族
    minzu = "".join(et.xpath('//*[@id="mw-content-text"]/div/div[3]/div/div[2]/div/div[2]/table/tbody/tr[2]/td[1]/text()')).strip()
    # 生日
    n = random.randint(1998, 2004)
    y = random.randint(1, 12)
    r = random.randint(1, 31)
    birs = f"{str(n)}-{str(y)}-{str(r)}"
    # 政治面貌
    face = "群众"
    # 文化程度
    degree = "".join(et.xpath('//*[@id="mw-content-text"]/div/div[3]/div/div[2]/div/div[2]/table/tbody/tr[1]/td[2]/text()')).strip()
    wenhua = ""
    if degree == '1' :
        wenhua = "小学"
    elif degree == '2' :
        wenhua = "初中"
    elif degree == '3' :
        wenhua = "高中"
    elif degree == '4' :
        wenhua = "大专"
    elif degree == '5' :
        wenhua = "本科"
    elif degree == '6' :
        wenhua = "博士"
    # 婚姻情况
    hunyin = "未婚"
    # 籍贯
    jiguan = "".join(et.xpath('//*[@id="mw-content-text"]/div/div[3]/div/div[3]/div/div[1]/table/tbody/tr[2]/td/div/p/text()[2]')).strip().replace('【出身地】', '').replace('【真实姓名】', '').replace('【战斗经验】', '').replace('【专精】', '').replace('【出厂日】', '').replace('【生日】', '')
    # 身份证
    identity = str(random.randint(100000000, 200000000))
    # 手机号码
    phone = str(random.randint(100000000, 200000000))
    # 邮箱
    email = str(random.randint(10000, 20000)) + "@qq.com"
    # 入职时间
    ruzhi = "".join(et.xpath('//*[@id="mw-content-text"]/div/div[3]/div/div[2]/div/div[2]/table/tbody/tr[8]/td/a/text()')).strip().replace("年", "-").replace("月", "-").replace("日", "")
    # 员工级别
    jibie = '1'
    # 员工基本工资
    try:
        money = str(int("".join(et.xpath('//*[@id="mw-content-text"]/div/div[6]/div/div[1]/div/div[1]/table[2]/tbody/tr[1]/td[2]/text()')).strip())*1000)
    except:
        money = 0
    # 部门编号
    bumeng = random.randint(0,9)
    # 状态
    situ = "在职"
    # 密码
    password = bianhao
    # 身份
    iden = "员工"

    sql = f'INSERT INTO 员工 VALUES("{bianhao}","{name}","{xinbie}","{pic}","{minzu}","{birs}","{face}","{wenhua}","{hunyin}","{jiguan}","{identity}","{phone}","{email}","{ruzhi}","{jibie}","{money}","{bumeng}","{situ}","{password}","{iden}");'
    print(sql)
    try:
        cursor.execute(sql)  # 执行sql语句

        db.commit()  # COMMIT命令用于把事务所做的修改保存到数据库
    except Exception:
        db.rollback()  # 发生错误时回滚
        print("插入")
    bianhao += 1


cursor.close()  # 关闭游标
db.close()  # 关闭数据库连接
