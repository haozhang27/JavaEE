# Mybatis

## 1、简介

### 1.1 什么是Mybatis

- MyBatis 是一款优秀的**持久层**框架

- 它支持自定义 SQL、存储过程以及高级映射。

- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。
- MyBatis 本是apache的一个[开源项目](https://baike.baidu.com/item/开源项目/3406069)iBatis, 2010年这个[项目](https://baike.baidu.com/item/项目/477803)由apache software foundation 迁移到了[google code](https://baike.baidu.com/item/google code/2346604)，并且改名为MyBatis 。
- 2013年11月迁移到[Github](https://baike.baidu.com/item/Github/10145341)。



如何获取Mybatis？

- maven 仓库

  ```xml
  `<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
  <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.2</version>
  </dependency>`
  ```

  

- GitHub：https://github.com/mybatis/mybatis-3

- 中文文档：https://mybatis.org/mybatis-3/zh/index.html

### 1.2 持久层

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
- 内存：**断电即失**
- 数据库(jdbc)，io文件持久化
- 生活：冷藏、罐头

**为什么需要持久化？**

- 有一些对象，不能让他丢掉
- 内存太贵了

### 1.3 持久层

Dao层、Service层、Controller层...

- 完成持久化工作的代码块
- 层界限什么明显

### 1.4 为什么需要Mybatis？

- 帮程序员将数据库存入到数据库中

- 方便
- 传统的JDBC代码太复杂了。
- 不用Mybatis也可以，更容易上手。
- 优点
  + 简单易学
  + 灵活
  + sql和代码的分离，提高可维护性
  + 提供映射标签，支持对象与数据库的orm字段关系映射
  + 提供对象关系映射标签，支持对象关系组建维护
  + 提供xml标签，支持编写动态sql。

## 2、第一个Mybatis程序

思路：搭建环境 --> 导入Mybatis --> 编写代码 --> 测试

### 2.1 搭建环境

搭建数据库

```sql
create database mybatis;

use mybatis;

create table user(
    id int(20) primary key,
    name varchar(30) default null,
    pwd varchar(30) default null
)engine=innodb default charset=utf8;

insert into user (id, name, pwd) VALUES
(1, '张三', '123'),
(2, '李三', '123'),
(3, '王三', '123')
```

新建项目

1.新建一个普通maven项目

2.删除src目录

3.导入maven依赖

### 2.2 创建一个模块

- 编写mybatis核心配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSl=true&amp;useUnicode=true&amp;characterEncoding=utf8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="love=water"/>
              </dataSource>
          </environment>
      </environments>
  
  </configuration>
  ```

- 编写mybatis工具类

  ```java
  package com.hao.utils;
  
  import org.apache.ibatis.io.Resources;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;
  import org.apache.ibatis.session.SqlSessionFactoryBuilder;
  
  import java.io.IOException;
  import java.io.InputStream;
  
  /**
   * sqlSessionFactory --> sqlSession
   *
   * @author haozhang
   * @date 2019/09/19
   */
  public class MybatisUtils {
  
      private static SqlSessionFactory sqlSessionFactory;
  
      static {
          try {
              //使用mybatis第一步：获取sqlSessionFactory对象
              String resource = "org/mybatis/example/mybatis-config.xml";
              InputStream inputStream = Resources.getResourceAsStream(resource);
              SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  
  
      //既然有了 SqlSessionFactory，顾名思义，我们可以从中获得 SqlSession 的实例。
      // SqlSession 提供了在数据库执行 SQL 命令所需的所有方法。你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。
  
      public static SqlSession getSqlSession() {
          return sqlSessionFactory.openSession();
      }
  }
  
  ```

### 2.3 编写代码

- 实体类

  ```java
  package com.hao.pojo;
  
  /**
   * Demo class
   *
   * @author haozhang
   * @date 2019/09/19
   */
  public class User {
  
      private int id;
  
      private String name;
  
      private String pwd;
  
      @Override
      public String toString() {
          return "User{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", pwd='" + pwd + '\'' +
                  '}';
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public void setPwd(String pwd) {
          this.pwd = pwd;
      }
  
      public int getId() {
          return id;
      }
  
      public String getName() {
          return name;
      }
  
      public String getPwd() {
          return pwd;
      }
  }
  
  ```

  

- Dao接口

  ```java
  package com.hao.dao;
  
  import com.hao.pojo.User;
  
  import java.util.List;
  
  public interface UserDao {
      List<User> getUserList();
  }
  
  ```

  

- 接口实现类由原来的UserDaoImp转变为一个Mapper配置文件

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.hao.dao.UserDao">
      <select id="getUserList" resultType="com.hao.pojo.User">
          select * from mybatis.user;
      </select>
  </mapper>
  ```

  

### 2.4 测试

**MapperRegistry是什么？**

核心配置文件中注册mappers

- jutil测试

  ```java
  package com.hao.dao;
  
  import com.hao.pojo.User;
  import com.hao.utils.MybatisUtils;
  import org.apache.ibatis.session.SqlSession;
  import org.junit.Test;
  
  import java.util.List;
  
  /**
   * Demo class
   *
   * @author haozhang
   * @date 2019/09/19
   */
  public class UserDaoTest {
      @Test
      public void test() {
          //1.获取sqlSession对象
          SqlSession sqlSession = MybatisUtils.getSqlSession();
  
          //2.执行sql语句
          UserDao userDao = sqlSession.getMapper(UserDao.class);
          List<User> userList = userDao.getUserList();
  
          for (User user : userList) {
              System.out.println(user);
          }
  
          //关闭sqlSession
          sqlSession.close();
      }
  }
  
  ```

  

可能会遇到的问题

- 配置文件没注册
- 绑定接口错误
- 方法名不对
- 返回类型不对
- Maven导出资源问题



## 3、CRUD

### 3.1 namespace

namespace中的包名要和Dao/mapper接口的包名一致

### 3.2 select

查询语句

- id：就是对应namespace中的方法名
- resultType：Sql语句执行的返回值
- parameterType：参数类型



1.编写接口

```java
User getUserById(int id);
```

2.编写对应Mapper中的sql语句

```xml
<select id="getUserById" parameterType="int" resultType="com.hao.pojo.User">
        select * from mybatis.user where id = #{id}
</select>
```

3.测试

```java
    @Test
    public void testGetUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.getUserById(1);
        System.out.println(user);

        sqlSession.close();
    }
```



### 3.3 insert

```xml
<insert id="addUser" parameterType="com.hao.pojo.User">
        insert into mybatis.user (id, name, pwd) values (#{id}, #{name}, #{pwd})
</insert>
```

### 3.4 update

```xml
<update id="updateUser" parameterType="com.hao.pojo.User">
        update mybatis.user set name=#{name}, pwd=#{pwd} where id=#{id};
</update>
```

### 3.5 delete

```xml
<delete id="deleteUser" parameterType="int">
        delete from mybatis.user where id=#{id}
</delete>
```

### 3.6 可能会出错的地方

**标签匹配不能出错**

- 在UserMapper.xml文件中

```xml
<mapper namespace="com.hao.dao.UserMapper">
```

​     这里namespace的路径必须是 **包名.** 不能用 **包名/**

- 在mybatis-config.xml文件中

```xml
<mappers>
        <mapper resource="com/hao/dao/UserMapper.xml"/>
 </mappers>
```

​     这里resource的路径必须是 **包名/**，在这里不能用 **包名.**

### 3.7 Map

假设，我们的实体类或者数据库中的表，字段或者参数过多，我们应当考虑使用Map！



```java
int insertUser(Map<String, Object> map);
```

```xml
<insert id="insertUser" parameterMap="map">
        insert into mybatis.user (id, name, pwd) values (#{userId}, #{userName}, #{password})
</insert>
```

```java
    @Test
    public void testInsertUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        HashMap<String, Object> map = new HashMap<>(16);
        map.put("userId", 4);
        map.put("userName", "hello");
        map.put("password", "222");

        int res = mapper.insertUser(map);
        if (res > 0) {
            System.out.println("插入成功");
        }

        sqlSession.commit();
        sqlSession.close();
    }
```



Map传递传递参数，直接在sql中取出key即可 【parameterType="map"】

对象传递参数，直接在sql中取对象属性即可   【parameterType="任何一个对象（比如说自己定义的User）"】

只有一个基本类型参数的情况下，可直接在sql中取到

多个参数用Map，**或者注解！**

### 3.8 模糊查询怎么写？

​	1.Java代码执行的时候，传递通配符 % %

```java
List<User> list = mapper.getUserLike("%李%");
```

​	2.在sql拼接中使用通配符

```xml
select * from mybatis.user where name like %#{val}%
```

这样做可能会出现sql注入问题，

```java
select * from mybatis.user where id = ?
    
正常操作：select * from mybatis.user where id = 1
    
sql注入：select * from mybatis.user where id = 1 or 1=1
```

## 4、配置解析

### 4.1 核心配置文件

- mybatis-config.xml
- MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息

```xml
configuration（配置）
	properties（属性）
	settings（设置）
	typeAliases（类型别名）
	typeHandlers（类型处理器）
	objectFactory（对象工厂）
	plugins（插件）
	environments（环境配置）
		environment（环境变量）
			transactionManager（事务管理器）
			dataSource（数据源）
	databaseIdProvider（数据库厂商标识）
	mappers（映射器）
```

### 4.2 环境配置（environments）

Mybatis可以配置成适应多种环境

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

学会使用配置多套运行环境！

Mybatis默认的事务管理器就是JDBC，连接池：POOLED

### 4.3 属性（properties）

我们可以使用properties属性来实现引用配置文件

这些元素都是可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置



1.编写一个配置文件

db.properties

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf-8
username=root
password=love=water
```

2.在核心配置文件中引入

```xml
<properties resource="db.properties">
        <property name="username" value="root"/>
        <property name="password" value="11"/>
</properties>
```

- 可以直接引入外部文件
- 可以在其中增加一些属性配置
- 如果两个文件有同一个字段，优先使用外部配置文件

### 4.4 类型别名（typeAliases）

- 类型别名可为 Java 类型设置一个缩写名字。 
- 它仅用于 XML 配置，意在降低冗余的全限定类名书写。

```xml
<!--可以给实体类起名-->
    <typeAliases>
        <typeAlias type="com.hao.pojo.User" alias="User"/>
    </typeAliases>
```

也可以指定一个包名，Mybatis在包名下面搜索需要的JavaBean，例如

扫描实体类的包，它的默认别名就是这个类的类名，首字母小写

```xml
<typeAliases>
        <package name="com.hao.pojo"/>
</typeAliases>
```

在实体类比较少的时候，使用第一种方式。

如果实体类十分多，使用第二种方式。

第一种可以diy别名，第二种则不行，如果非要diy别名，则需要在实体类上增加注解

```java
@Alias("user")
public class User {
}
```

### 4.5 设置（settings）

![image-20201202142652459](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202142652459.png)

![image-20201202142709992](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202142709992.png)

### 4.6 映射器（mappers）

MapperRegistry：注册绑定我们的Mapper文件

方式一【最好使用这种】

```xml
<!--每一个Mapper.xml 都需要再Mybatis核心配置文件中注册-->
    <mappers>
        <mapper resource="com/hao/dao/UserMapper.xml"/>
    </mappers>
```

方式二：使用class文件绑定注册

```xml
<!--每一个Mapper.xml 都需要再Mybatis核心配置文件中注册-->
    <mappers>
        <mapper resource="com.hao.dao.UserMapper"/>
    </mappers>
```

注意点：

- 接口和它的Mapper配置文件必须同名
- 接口和它的配置文件必须在同一个包下

方式三：使用扫描包进行注入绑定

```xml
<!--每一个Mapper.xml 都需要再Mybatis核心配置文件中注册-->
    <mappers>
        <mapper resource="com.hao.dao"/>
    </mappers>
```

注意点：

- 接口和它的Mapper配置文件必须同名
- 接口和它的配置文件必须在同一个包下



需要掌握的：

- 将数据库配置文件外部引入

- 实体类别名
- 保证UserMapper接口 和 UserMapper.xml 为一致，并且放在同一个包下

### 4.7 作用域（Scope）和周期

生命周期和作用域是至关重要的，因为错误使用会导致严重**并发问题**

![image-20201202150003411](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202150003411.png)

**SqlSessionFactoryBuilder**

- 一旦创建SqlSessionFactory，就不再需要它了
- 局部变量

**SqlSessionFactory**

- 说白了就可以想象为：数据库连接池
- SqlSessionFactory 一旦被创建在程序运行期间就一直存在，没有理由丢弃它或者重新创建一个实例
- SqlSessionFactory的最佳作用域是应用作用域
- 最简单就是使用**单例模式**或者**静态单例模式**

**SqlSession**

- 连接到连接池的一个请求
- SqlSession的实例不是线程安全的，因此是不能被共享的，所以它的最佳作用域是请求或者方法的作用域。
- 用完之后需要赶紧关闭，否则资源被占用

![image-20201202151011563](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202151011563.png)

这里的每一个Mapper就代表一个具体的业务。

## 5、解决属性名和字段名不一致的问题

#### 5.1 问题 属性名，字段名不一致会导致查询不到该字段

数据库中的字段

![image-20201202151229603](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202151229603.png)

新建一个项目，拷贝之前之前的，测试实体类字段不一致的情况

```java
public class User {
    private int id;
    private String name;
    private String password;
}
```



解决方法：

- 起别名

```xml
<select id="getUserById" parameterType="int" resultType="com.hao.pojo.User">
        select id, name, pwd as password from mybatis.user where id = #{id}
</select>
```

#### 5.2 ResultMap

结果集映射

```java
id	name	pwd
id	name	password
```

```xml
<!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!--column数据库中的字段， property实体类中的属性-->
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="pwd" property="password"/>
    </resultMap>

    <select id="getUserById" resultMap="UserMap">
        select * from mybatis.user where id = #{id}
    </select>
```

- resultMap 元素是Mybatis中最重要最强大的元素
- ResultMap 的设计思想是，对简单的语句做到零配置，对于复杂一点的语句，只需要描述语句之间的关系就行了。
- `ResultMap` 的优秀之处在于不需要显式配置 `ResultMap`

## 6、日志

### 6.1 日志工厂

如果一个数据库操作，出现了异常，我们需要排错。日志就是最好的助手。

曾经：sout debug

现在：日志工厂

logImpl：指定Mybatis所用日志的具体实现，未指定时将自动查找。

- LOG4J
- STDOUT_LOGGING

在Mybatis中具体使用哪一个日志实现，在设置中设定！

**STDOUT_LOGGING标准日志输出**

在mybatis核心配置文件中，配置日志！

```xml
<settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```



![image-20201202164445195](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202164445195.png)

### 6.2 Log4j

什么是Log4j？

- Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件
- 我们也可以控制每一条日志的输出格式
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程
- 可以通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置，而不需要修改应用的代码

**Log4j的使用步骤**

1.先导入Log4j的包

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

```

2.log4j.properties

```properties
#将等级分为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=debug,console,file 

#console 输出的相关设置
log4j.appender.console=org.apache.log4j.ConsoleAppender 
log4j.appender.console.Target=System.out
log4j.appender.console.Threshold=debug
log4j.appender.console.layout=org.apache.log4j.PatternLayout 
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出相关设置
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/hao.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=debug
log4j.appender.file.layout=org.apache.log4j.PatternLayout 
log4j.appender.file.layout.ConversionPattern=[%p][%d{yyyy-MM-dd HH:mm:ss}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=debug
log4j.logger.java.sql=debug
log4j.logger.java.sql.Statement=debug
log4j.logger.java.sql.ResultSet=debug
log4j.logger.java.sql.PreparedStatement=debug
```

3.配置log4j为日志的实现

```xml
<settings>
        <setting name="logImpl" value="LOG4J"/>
</settings>
```

4.Log4j的使用 直接测试运行刚才的查询

![image-20201202174750077](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202174750077.png)



**简单使用**

​	1.再要使用Log4j的类中，导入包 import org.apache.log4j.Logger;

​	2.日志对象，参数为当前类的class

```java
static Logger logger = Logger.getLogger(UserMapperTest.class);
```

​	3.日志级别

```java
logger.info("info:进入了testLog4j");
logger.debug("debug:进入了testLog4j");
logger.error("error:进入了testLog4j");
```

## 7、分页

为什么要分页？

- 减少数据的处理量



### **7.1 使用limit分页**

```sql
语法：select * from user limit startIndex,pageSize;
select * from user limit 3; #[0, n]
```



使用Mybatis实现分页，核心SQL

​	1.接口

```java
List<User> getUserByLimit(Map<String, Integer> map);
```

​	2.Mapper.xml

```xml
<!--结果集映射-->
    <resultMap id="UserMap" type="User">
        <!--column数据库中的字段， property实体类中的属性-->
        <result column="pwd" property="password"/>
    </resultMap>

<select id="getUserByLimit" parameterType="map" resultMap="UserMap">
        select * from mybatis.user limit #{startIndex}, #{pageSize}
</select>
```

​	3.测试

```java
@Test
    public void testGetUserByLimit() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Map<String, Integer> map = new HashMap<>(16);
        map.put("startIndex", 0);
        map.put("pageSize", 2);
        List<User> userList = mapper.getUserByLimit(map);
        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }
```

### 7.2 RowBounds分页

不再使用SQL分页

​	1.接口

```java
List<User> getUserByBounds();
```

​	2.Mapper.xml

```xml
<select id="getUserByRounds" resultMap"userMap">
	select * from mybatis.user
</select>
```

​	3.测试

```java
@Test
    public void testGetUserByBounds() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        RowBounds rowBounds = new RowBounds(1, 2);

        List<User> userList = sqlSession.selectList("com.hao.dao.UserMapper.getUserByRounds");
        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }
```

### 7.3 分页插件

![image-20201202183702379](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202183702379.png)

## 8、使用注解开发

### 8.1 什么叫面向接口编程

为什么要使用接口？

**解耦**

### 8.2 使用注解开发

1.注解在接口上实现

```java
@Select("select * from user")
List<User> getUsers();
```

2.需要在核心配置文件中绑定接口

```xml
<!--绑定接口-->
    <mappers>
        <mapper class="com.hao.dao.UserMapper"/>
    </mappers>
```

3.测试

```java
    @Test
    public void testGetUsers() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //底层主要应用反射
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> userList = mapper.getUsers();

        for (User user : userList) {
            System.out.println(user);
        }

        sqlSession.close();
    }
```



本质：反射机制实现

底层：动态代理



**Mybatis执行流程**

![image-20201202192859282](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201202192859282.png)

### 8.3 CRUD

我们可以在工具类创建的时候自动提交事务

```java
public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
}
```

编写接口，增加注解

```java
/**
     * 方法存在多个参数，所有参数前都加@Param（基本数据类型参数）
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id")int id);

    @Insert("insert into user(id, name, pwd) values (#{id}, #{name}, #{password})")
    int addUser(User user);

    @Update("update user set name=#{name}, pwd=#{password} where id=#{id}")
    int updateUser(User user);

    @Delete("delete from user where id=#{uid}")
    int deleteUser(@Param("uid") int id);
```

测试类

【注意：必须将接口绑定到核心配置到文件中】



**关于@Param()注解**

- 基本数据类型的参数或者String类型需要加上
- 引用类型不需要加
- 如果只有一个基本数据类型参数，可以忽略，最好加上！
- sql中引用的就是我们这里的@Param中设定的属性名！

**#{}可以防止sql注入，${}不能**

## 9、Lombok

```xml
<!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.12</version>
    <scope>provided</scope>
</dependency>

```



说明：

```text
@Data：无参构造，get、set、tostring、hashcode、equals
@AllArgsConstructor
@NoArgsConstructor
@EqulasAndHashCode
@ToString
@Getter
```

## 10、多对一处理

**多对一：**

![image-20201203093819384](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201203093819384.png)

- 多个学生对应一个老师
- 对于学生而言， **关联**   多个学生关联一个老师【多对一】
- 对于老师而言， **集合**   一个老师，有很多学生【一对多】

![image-20201203103313232](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201203103313232.png)

SQL：

```sql
CREATE TABLE teacher (
	id int(10) PRIMARY key,
	name VARCHAR(30) DEFAULT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO teacher(id, name) VALUES(1, '张老师');

CREATE TABLE student(
	id int(10) PRIMARY KEY,
	name VARCHAR(30) DEFAULT null,
	tid int(10) DEFAULT null,
	CONSTRAINT fktid FOREIGN KEY (tid) REFERENCES teacher (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO student (id, name, tid) values(1, '小明', 1),
			(2, '小红', 1),
			(3, '小张', 1),
			(4, '小王', 1),
			(5, '小李', 1);
```

### **10.1 测试环境搭建**

1、导入lombok

2.创建实体类Teacher、Student

3、建立Mapper接口

4、建立Mapper.xml文件

5、在核心配置文件中绑定注册Mapper接口或者文件

6、测试查询是否成功

### **10.2 按照查询嵌套处理**

```xml
    <select id="getStudentList" resultMap="StudentTeacher">
        select * from student;
    </select>
    <resultMap id="StudentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--复杂的属性需要单独处理
                对象：association
                集合：collection
        -->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
    </resultMap>

    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id=#{id};
    </select>
```

### **10.3 按照结果嵌套处理**

```xml
    <!--按照结果嵌套处理-->
    <select id="getStudentList2" resultMap="StudentTeacher2">
        select s.id sid, s.name sname, t.name tname
        from student s, teacher t
        where s.tid = t.id;
    </select>

    <resultMap id="StudentTeacher2" type="student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```

回顾Mysql多对一查询方式：

- 子查询
- 联表查询

## 11、一对多处理

比如：一个老师拥有多个学生！

对于老师而言，就是一对多的关系！



### 11.1 环境搭建

​	1、实体类

```java
@Data
public class Student {
    private int id;
    private String name;
    private int tid;
}
```

```java
@Data
public class Teacher {
    private int id;
    private String name;

    /**
     * 一个老师对应多个学生
     */
    private List<Student> students;
}
```

### 11.2 按照结果嵌套处理

```xml
    <!--按照结果嵌套查询-->
    <select id="getTeacher" resultMap="TeacherStudent">
        select t.name tname, s.id sid, s.name sname, t.id tid
        from mybatis.teacher t, mybatis.student s
        where s.tid = t.id and t.id=#{tid};
    </select>

    <resultMap id="TeacherStudent" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
        <!--复杂属性，需要单独处理 对象：association 集合：collection
            javaType="" 指定属性的类型
            集合中的泛型信息，我们使用ofType获取
        -->
        <collection property="students" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
```



### 11.3 按照查询嵌套处理

```xml
<select id="getTeacher2" resultMap="TeacherStudent2">
        select * from mybatis.teacher where id=#{tid};
    </select>

    <resultMap id="TeacherStudent2" type="Teacher">
        <collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id"/>
    </resultMap>

    <select id="getStudentByTeacherId" resultType="Student">
        select * from mybatis.student where tid=#{tid};
    </select>
```

### 11.4 小结

1.关联-association 【多对一】

2.集合-connection 【一对多】

3.javaType & ofType

​		1.javaType 用来指定实体类中属性的类型

​		2.ofType 用来指定映射到List或者集合中的pojo类型，泛型中的约束类



**注意点**：

- 保证sql的可读性
- 注意一对多和多对一中，属性名和字段的问题
- 可使用日志排查错误 最好log4j

## 12、动态SQL

**什么是动态SQL？**

==**动态SQL就是指根据不同的条件生成不同的SQL语句**==

### 12.1 搭建环境

```sql
create table blog(
    id varchar(50) not null,
    title varchar(100) not null comment '博客标题',
    author varchar(30) not null,
    create_time datetime not null,
    views int(30) not null comment '浏览量'
)engine=InnoDB default charset=utf8
```

创建基础工厂

​	1.导包

​	2.编写配置文件

​	3.编写实体类

```java
@Data
public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
```



​	4.编写实体类对应Mapper接口和Mapper.xml文件

### 12.2 if

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
        select * from mybatis.blog where 1=1
        <if test="title != null">
            and title=#{title}
        </if>

        <if test="author != null">
            and author=#{author}
        </if>
    </select>
```

一个参数匹配不到就结束。必须匹配所有参数

### 12.3 choose（when， otherwise）

```xml
<select id="queryBlogChoose" parameterType="map" resultType="blog">
        select * from mybatis.blog
        <where>
            <choose>
                <when test="title != null">
                    title=#{title}
                </when>
                <when test="author != null">
                    author=#{author}
                </when>

                <otherwise>
                    and views=#{views}
                </otherwise>
            </choose>
        </where>
    </select>
```

如果匹配到一个后面的就不匹配了，就直接输出了。where会智能去掉**and**

### 12.4 trim（where， set）

```xml
<update id="updateBlog" parameterType="map">
        update mybatis.blog
        <set>
            <if test="title != null">
                title=#{title},
            </if>
            <if test="author != null">
                author=#{author}
            </if>
        </set>
        where id = #{id}
    </update>
```

set会智能去掉 “**,**”

==**动态SQL的本质还是SQL语句，只是可以在SQL层面，去执行一个逻辑代码**==

### 12.5 SQL片段

有的时候，我们可以将一些功能部分抽取出来，方便复用

​	1.使用SQL标签抽取公共的部分

```xml
<sql id="if-title-author">
        <if test="title != null">
            and title=#{title}
        </if>

        <if test="author != null">
            and author=#{author}
        </if>
    </sql>
```

​	2.在需要使用的地方使用include标签引用即可

```xml
<select id="queryBlogIF" parameterType="map" resultType="blog">
        select * from mybatis.blog
        <where>
            <include refid="if-title-author"></include>
        </where>
    </select>
```



注意事项：

- 最好基于单表来定义sql片段
- 不要存在where标签

### 12.6 Foreach

```sql
select * from user where 1=1 and 

 <foreach item="id" collection="ids"
      open="(" separator="or" close=")">
        #{id}
  </foreach>



(id=1 or id=2 or id=3);
```

![image-20201204081600414](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201204081600414.png)

```xml
<!--select * from user where 1=1 and (id=1 or id=2 or id=3);
        我们传递一个map，这个map中可以存在一个集合
    -->
    <select id="queryForeach" parameterType="map" resultType="Blog">
        select * from mybatis.blog
        
        <where>
            <foreach collection="ids" item="id" open="and (" close=")" separator="or">
                id=#{id}
            </foreach>
        </where>
    </select>
```

==动态sql就是在拼接sql语句，我们只要保证sql的正确性，按照sql 的格式去掉排列组合==

步骤：

- 先在mysql中写出完整sql，然后在数据库中去执行一下，再去xml中搞

## 13、缓存

```text
查询 ： 连接数据库，耗资源！
	一次查询的结果，给他暂存在一个可以直接取到的地方！ --> 内存 ： 缓存
	
我们再次查询相同数据的时候，直接走缓存，就不用走数据库了
```



### 13.1 简介

**1.什么是缓存[Cache]？**

- 存在内存中的临时数据
- 将用户经常查询的数据放在缓存（内存）中，用户查询数据就不用从磁盘上(关系型数据库数据文件)查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。

**2.为什么使用缓存？**

- 减少和数据库的交互次数，减少系统开销，提高系统效率

**3.什么样的数据能使用缓存？**

- 经常查询并不经常改变的数据

### 13.2 Mybatis缓存

- MyBatis包含一个非常强大的查询缓存特性，它可以方便定制和配置缓存。缓存可以极大的提升查询效率。
- Mybatis系统中默认定义了两级缓存：**一级缓存**和**二级缓存**
  - 默认情况下，只有一级缓存开取。（SqlSession级别的缓存，也称为本地缓存）
  - 二级缓存需要手动开启和配置，它是基于namespace级别的缓存
  - 为了提高扩展性，Mybatis定义了缓存接口Cache。我们可以实现Cache接口来定义二级缓存

### 13.3 一级缓存

- 一级缓存也叫本地缓存
  - 与数据库同一次会话期间查询到的数据会被放在本地缓存中
  - 以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库



测试步骤：

​	1.开启日志

​	2.测试在一个session中查询两次相同记录

​	3.查看日志输出

![image-20201204094902624](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201204094902624.png)

缓存失效的情况

​	1.查询不同的东西

​	2.增删改操作，可能会改变原来的数据，所以必定会刷新缓存！

![image-20201204100026661](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201204100026661.png)

​	3.查询不同的Mapper

​	4.手动清理缓存

![image-20201204100156123](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201204100156123.png)

小结：一级缓存是默认开启的，只在一次SqlSession中有效，也就是拿到连接到关闭连接这个区间段！

一级缓存相当于一个Map。

### 13.4 二级缓存

- 二级缓存也叫全局缓存，一级缓存作用域太低了，所以诞生了二级缓存
- 基于namespace级别的缓存，一个名称空间，对应一个二级缓存
- 工作机制
  - 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中
  - 如果当前会话关闭了，这个会话对应的一级缓存就没了，但是我们想要的是，会话关闭了，一级缓存中的数据被保存到二级缓存中
  - 新的会话查询信息，就可以从二级缓存中获取内容
  - 不同的mapper查出的数据会放在自己对应的缓存（map）中

**步骤：**

​	1.开启全局缓存

```xml
<!--开启一级缓存-->
<setting name="cacheEnabled" value="true"/>
```

​	2.在要使用二级缓存的Mapper中开启

```xml
<!--在当前Mapper.xml中使用二级缓存-->
    <cache/>
```

​	也可以自定义参数

```xml
<!--在当前Mapper.xml中使用二级缓存-->
    <cache eviction="FIFO"
           flushInterval="60000"
           size="512"
           readOnly="true"
    />
```

​	3.测试

​		1.我们需要将实体类序列化！否则就会报错！

​		

小结：

- 只要开启了二级缓存，在同一个Mapper下就有效
- 所有的数据都会先放在一级缓存中
- 只有当会话提交或者关闭的时候，才会提交到二级缓存

### 13.5 缓存原理

![image-20201204110329811](C:\Users\haozhang\AppData\Roaming\Typora\typora-user-images\image-20201204110329811.png)

### 13.6 自定义缓存-ehcache

```text
Ehcache是一种广泛使用的开源Java分布式缓存，主要面向通用缓存
```



要在程序中使用ehcache，先要导包

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.caches/mybatis-ehcache -->
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.1.0</version>
</dependency>

```

在mapper中指定使用我们的ehcache缓存实现

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```



ehcache.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--
       diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
       user.home – 用户主目录
       user.dir  – 用户当前工作目录
       java.io.tmpdir – 默认临时文件路径
     -->
    <diskStore path="java.io.tmpdir/Tmp_EhCache"/>
    <!--
       defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
     -->
    <!--
      name:缓存名称。
      maxElementsInMemory:缓存最大数目
      maxElementsOnDisk：硬盘最大缓存个数。
      eternal:对象是否永久有效，一但设置了，timeout将不起作用。
      overflowToDisk:是否保存到磁盘，当系统宕机（系统崩了）时
      timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
      timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
      diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
      diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
      diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
      memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
      clearOnFlush：内存数量最大时是否清除。
      memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
      FIFO，first in first out，这个是大家最熟的，先进先出。
      LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
      LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
   -->
    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259200"
            memoryStoreEvictionPolicy="LRU"/>

    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>

</ehcache>
```

