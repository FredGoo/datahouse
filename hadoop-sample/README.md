## hadoop r2.9.0 windows下开发环境

1. 把winutils下的hadoop-2.8.1.zip解压到本机目录下,如 D:/hadoop/hadoop-2.8.1/bin

2. 系统添加环境变量,HADOOP_HOME D:/hadoop/hadoop-2.8.1.此时运行hadoop还是会报  

        org.apache.hadoop.io.nativeio.NativeIO$Windows.access0

3. 建一个org.apache.hadoop.io.nativeio的包,然后拷贝源码中的NativeIO.java文件,修改其中的access方法如下

        public static boolean access(String path, NativeIO.Windows.AccessRight desiredAccess) throws IOException {
        //            return access0(path, desiredAccess.accessRight());
            return true;
        }