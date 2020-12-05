import com.hao.dao.BlogMapper;
import com.hao.pojo.Blog;
import com.hao.utils.IDUtils;
import com.hao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;

/**
 * Demo class
 *
 * @author haozhang
 * @date 2019/09/19
 */
public class AddTest {

    @Test
    public void testAddBlog() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(IDUtils.getId());
        blog.setTitle("Mybatis");
        blog.setAuthor("hao");
        blog.setCreateTime(new Date());
        blog.setViews(4456);

        mapper.addBlog(blog);


        blog.setId(IDUtils.getId());
        blog.setTitle("Java");
        mapper.addBlog(blog);

        blog.setId(IDUtils.getId());
        blog.setTitle("Spring");
        mapper.addBlog(blog);

        blog.setId(IDUtils.getId());
        blog.setTitle("SpringCloud");
        mapper.addBlog(blog);



        sqlSession.close();
    }

    @Test
    public void testQueryBlogIF() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        //map.put("title", "Spring");
        //map.put("author", "hao");
        map.put("views", 4456);
        List<Blog> blogs = mapper.queryBlogChoose(map);

        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }

    @Test
    public void updateBlog() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap();
        map.put("title", "Spring2");
        map.put("author", "hao");
        map.put("id", "76ba64b4cccf490a9d164fac1d7a2773");


        mapper.updateBlog(map);

        sqlSession.close();
    }

    @Test
    public void queryForeach() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Map map = new HashMap<>();

        List<String> ids = new ArrayList<>();
        ids.add("76ba64b4cccf490a9d164fac1d7a2773");
        ids.add("21aa825cd6364c65b6e087ee8ea0e632");
        ids.add("876d6b04ddbe4ab98ceb29295c31d4f3");
        map.put("ids", ids);

        List<Blog> blogs = mapper.queryForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }


}
