package config;

import dao.BarangDao;
import dao.BarangDaoImpl;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration(proxyBeanMethods = false)
public class SpringConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/db_erp_toko?useSSL=false&serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword(""); // sesuaikan jika MySQL XAMPP-mu pakai password
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public BarangDao barangDao(JdbcTemplate jdbcTemplate) {
        return new BarangDaoImpl(jdbcTemplate);
    }
}