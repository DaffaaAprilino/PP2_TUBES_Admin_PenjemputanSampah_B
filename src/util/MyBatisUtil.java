package util;

import java.io.Reader;
import java.util.Objects;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
  private static SqlSessionFactory sqlSessionFactory;

  static {
    try {
      Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error initializing MyBatis", e);
    }
  }

  public static SqlSession getSession() {
    return Objects.requireNonNull(sqlSessionFactory).openSession();
  }
}