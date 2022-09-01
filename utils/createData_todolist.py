import random
import time
import pymysql
from faker import Faker

class createData:
    def __init__(self):
        self.conn = pymysql.connect(
            host="152.136.154.181",
            user="teashirt",
            password="123456",
            port=3306,
            database="todolist"
        )
        self.cur = self.conn.cursor()
        self.fk = Faker(locale="zh-CN")

    def __del__(self):
        self.cur.close()
        self.conn.close()

    def create(self, dataNum):
        sql = f'''
        INSERT INTO `todolist` (userid, content, status) VALUES (
            '1', %s, %s
        )
        '''
        contents = []
        for i in range(int(dataNum)):
            contents.append([self.fk.company(), random.randint(0, 1)])
        try:
            self.cur.executemany(sql, contents)
            self.conn.commit()
        except pymysql.Error as e:
            raise e


if __name__ == "__main__":
    a = createData()
    dataNum = int(input("[温馨提示] 给出创造数据的数量: "))
    st = time.perf_counter()
    a.create(dataNum=dataNum)
    ed = time.perf_counter()
    print(f"[创造数据] 在 {ed - st} 秒内完成")
