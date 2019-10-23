package gr.algo.GousteraCroftServer

import javafx.application.Application
import org.apache.catalina.core.ApplicationContext
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GousteraCroftServerApplication {

	fun restart() {
		val args = context?.getBean(ApplicationArguments::class.java)
		val thread = Thread({
			context?.close()
			context = SpringApplication.run(GousteraCroftServerApplication::class.java, *args?.sourceArgs)
		})
		thread.setDaemon(false)
		thread.start()
	}

}
lateinit var context: ConfigurableApplicationContext
	fun main(args: Array<String>) {
		//runApplication<GousteraCroftServerApplication>(*args)
		context=SpringApplication.run(GousteraCroftServerApplication::class.java, *args)

	}




