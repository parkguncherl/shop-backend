package com.shop.api.config;

import com.shop.core.enums.BooleanValueCode;
import com.shop.core.enums.SortTypeCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import software.amazon.awssdk.services.s3.model.JSONType;

import javax.sql.DataSource;

/**
 * <pre>
 * Description: MyBatis 설정
 * Date: 2023/01/26 12:35 PM
 * Company: smart
 * Author : luckeey
 * </pre>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class MybatisConfigure {

    @Qualifier("dataSource")
    private final DataSource dataSource;

    private TypeHandler[] typeHandlers = new TypeHandler[]{
            new LocalDateTimeTypeHandler(),
            new LocalDateTypeHandler(),
            new BooleanValueCode.TypeHandler(),
            new SortTypeCode.TypeHandler(),
            new JsonbTypeHandler(),  // 새로 추가
            new StringListTypeHandler() // 새로추가
    };

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/**/*.xml"));
        sqlSessionFactory.setTypeHandlers(typeHandlers);
        sqlSessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);     // 컬럼명 맵핑시 "_" 제외 시키기
        return sqlSessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager dataTxManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}