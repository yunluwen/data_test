package com.zyh.batch;


import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;

/**
 * join算子：
 * join将两个DataSet按照一定的关联度进行类似SQL中的Join操作。
 */
public class JoinFunction001java {

    public static void main(String[] args) throws Exception{
        // 1.设置运行环境，准备运行的数据
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // Author (id, name, email)
        DataSet<Tuple3<String, String, String>> authors = env.fromElements(
                new Tuple3<String, String, String>("A001", "zhangsan", "zhangsan@qq.com"),
                new Tuple3<String, String, String>("A001", "lisi", "lisi@qq.com"),
                new Tuple3<String, String, String>("A001", "wangwu", "wangwu@qq.com")
        );
        //Archive (title, author name)
        DataSet<Tuple2<String, String>> posts = env.fromElements(
                new Tuple2<String, String>("P001", "zhangsan"),
                new Tuple2<String, String>("P002", "lisi"),
                new Tuple2<String, String>("P003", "wangwu"),
                new Tuple2<String, String>("P004", "lisi")
        );
        // 2.用自定义的方式进行join操作
        DataSet<Tuple4<String, String, String,String>> join = authors.join(posts)
                .where(1).equalTo(1)
                //注意这里使用了with
                .with(new JoinFunction<Tuple3<String,String,String>, Tuple2<String,String>,
                        Tuple4<String, String, String,String>>() {  //注意这个返回类型必须设置
            @Override
            public Tuple4<String, String, String,String> join(Tuple3<String, String, String> author, Tuple2<String, String> post) {
                return new Tuple4<String, String, String,String>
                        (post.f0, author.f0, author.f1, author.f2);
            }
        });

        System.out.println(join.collect());
    }

}
