package com.bjtu.websystem.common.utils;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class linkToMySQL {
    public static void main(String[] args) throws IOException, SQLException {
        Connection conn=DriverManager.getConnection("jdbc:mySql://localhost:3306/jiaotongdata?serverTimezone=GMT%2B8", "root", "123456");
        Statement statement=conn.createStatement();
        String path="D:\\返校文件\\毕业设计\\ete软件\\EtE软件\\EtE软件\\ete1_dataset\\ete1_dataset\\link.csv";
        CsvReader reader=new CsvReader(path,',',Charset.forName("gbk"));
//        String name=new BufferedReader(new FileReader(new File(path))).readLine();
        String name = "id,cross1,cross2,a,b";
        //name就是表格列的名称
        reader.readHeaders();
        int len=reader.getHeaders().length; // 3列
//        System.out.println(len);
        //len表示的是有几个列
        while(reader.readRecord()){  //整个while就是为了组装成为 插入语句的形式
            String tmp="insert into link_tbl("+name+")values("+reader.get(0);
            for(int i=1;i<len-1;i++){
                tmp+=","+"'"+reader.get(i).replaceAll("'", "\\\\'")+"'";
            }
            tmp+=","+"'"+reader.get(len-1).replaceAll("'", "\\\\'")+"');";
            //tmp就是组装好的插入语句，即insert into talble(属性）values（内容）；
            statement.execute(tmp); //执行插入
        }
        reader.close();
        statement.close();
        conn.close();
    }
}
