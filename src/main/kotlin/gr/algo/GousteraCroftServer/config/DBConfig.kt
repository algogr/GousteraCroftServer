package gr.algo.GousteraCroftServer.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import javax.sql.DataSource

//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate


@Configuration
class DBConfig {
    @Bean(name = arrayOf("sqlite"))
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource1(): DataSource {


        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("org.sqlite.JDBC")
        dataSourceBuilder.url("jdbc:sqlite:filestorage/goustera.db")
        return dataSourceBuilder.build()



    }


    @Bean(name = arrayOf("jdbcTemplate1"))
    fun jdbcTemplate1(@Qualifier("sqlite") ds:DataSource):JdbcTemplate {
        return JdbcTemplate(ds)
    }



    @Bean(name = arrayOf("mysql"))
    @ConfigurationProperties(prefix = "spring.second-datasource")
    fun dataSource2(): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver")
        // dataSourceBuilder.url("jdbc:mysql://localhost:3306/goustera?serverTimezone=UTC")
        // dataSourceBuilder.username("root")
        // dataSourceBuilder.password("101264")
        dataSourceBuilder.url("jdbc:mysql://192.168.1.55:3306/ATLANTIS?serverTimezone=UTC")
        dataSourceBuilder.username("ATLANTIS")
        dataSourceBuilder.password("ATLANTIS")

        return dataSourceBuilder.build()
        //return  DataSourceBuilder.create().build()
    }


    @Bean(name = arrayOf("jdbcTemplate2"))
    fun jdbcTemplate2(@Qualifier("mysql") ds:DataSource):JdbcTemplate {
        return JdbcTemplate(ds)
    }



    @Bean(name = arrayOf("sqlite1"))
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource3(): DataSource {


        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName("org.sqlite.JDBC")
        dataSourceBuilder.url("jdbc:sqlite:filestorage/goustera.db.LATEST")
        return dataSourceBuilder.build()



    }


    @Bean(name = arrayOf("jdbcTemplate3"))
    fun jdbcTemplate3(@Qualifier("sqlite1") ds:DataSource):JdbcTemplate {
        return JdbcTemplate(ds)
    }



}

