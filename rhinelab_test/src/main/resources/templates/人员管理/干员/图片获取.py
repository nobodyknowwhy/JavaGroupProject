import os

import requests
from bs4 import BeautifulSoup

'''
注意，
    子页面的url如果开头是/，直接在前面拼接上域名即可
    子页面的url不是/开头，此时需要找到主页面的url，去掉最后一个/后面的所有内容，和当前获取到的url进行拼接

'''

# html = """
# <ul>
#     <li><a href="zhangwuji.com">张无忌</a></li>
#     <li id="abc"><a href="zhouxingchi.com">周星驰</a></li>
#     <li><a href="wuzetian.com">武则天</a></li>
#     <a href="jingmaoshiwang.com">金毛狮王</a>
# </ul>
# """

# 1.初始化BeautifulSoup对象
# page = BeautifulSoup(html, "html.parser")
# page.find("标签名", attrs={"属性": "值"})  # 查找某个元素，只会找到一个结果
# page.find_all("标签名", attrs={"属性": "值"}) # 找到一堆结果

# li = page.find("li", attrs={"id": "abc"})
# a = li.find("a")
# print(a.text) # 拿文本
# print(a.get("href")) # 拿属性. get("属性名")

# li_list = page.find_all("li")
# for li in li_list:
#     a = li.find("a")
#     text = a.text
#     href = a.get("href")
#     print(text, href)

# url = "http://www.xinfadi.com.cn/marketanalysis/0/list/1.shtml"
# resp = requests.get(url)
# # 初始化BS4对象
# page = BeautifulSoup(resp.text, "html.parser")
# table = page.find("table", attrs={"class":"hg_table"})
# trs = table.find_all("tr")[1:] # 此时拿到除了第一行外的所有tr
# for tr in trs:
#     tds = tr.find_all("td")
#     name = tds[0].text
#     low = tds[1].text
#     avg = tds[2].text
#     hig = tds[3].text
#     kind = tds[4].text
#     dan = tds[5].text
#     date = tds[6].text


url = "https://wiki.biligame.com/arknights/%E5%B9%B2%E5%91%98%E6%95%B0%E6%8D%AE%E8%A1%A8"
resp = requests.get(url)
resp.encoding = "utf-8"
main_page = BeautifulSoup(resp.text, "html.parser")
a_list = main_page.find_all("tr", attrs={"class": "divsort"})

dir_name = 'LinImage' # 创建文件夹
if not os.path.exists(dir_name):     #os模块判断并创建
        os.mkdir(dir_name)

for a in a_list:
    div = a.find("div", attrs={"class": "floatnone"})
    name = div.find("a").get("title")
    img_src = div.find("img").get("src") #  拿到图片的下载路径
    # 下载图片
    img_resp = requests.get(img_src)
    with open(dir_name+'/'+name+".jpg", mode="wb") as f:
        f.write(img_resp.content)
    print(f"图片{a_list.index(a)}下载完毕")