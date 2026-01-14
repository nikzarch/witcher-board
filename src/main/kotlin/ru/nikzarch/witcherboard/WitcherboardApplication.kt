package ru.nikzarch.witcherboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableJpaRepositories(
	basePackages = ["ru.nikzarch.witcherboard.repository.jpa"],
	entityManagerFactoryRef = "entityManagerFactory",
	transactionManagerRef = "transactionManager"
)

@EnableMongoRepositories(
	basePackages = ["ru.nikzarch.witcherboard.repository.mongo"],
	mongoTemplateRef = "mongoTemplate"
)
@SpringBootApplication
class WitcherboardApplication

fun main(args: Array<String>) {
	runApplication<WitcherboardApplication>(*args)
}
