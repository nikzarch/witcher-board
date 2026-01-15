package ru.nikzarch.witcherboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableJpaRepositories(
    basePackages = ["ru.nikzarch.witcherboard.repository",
                    "ru.nikzarch.mainservice.repository",
                    "ru.nikzarch.monsterservice.repository"],
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)

@EntityScan(
    basePackages = [
        "ru.nikzarch.witcherboard.domain",
        "ru.nikzarch.mainservice.domain",
        "ru.nikzarch.monsterservice.domain"
    ]
)

@EnableMongoRepositories(
    basePackages = ["ru.nikzarch.witcherboard.mongo"],
    mongoTemplateRef = "mongoTemplate"
)
@SpringBootApplication
@ComponentScan(
    basePackages = [
        "ru.nikzarch.witcherboard",
        "ru.nikzarch.mainservice",
        "ru.nikzarch.monsterservice"
    ]
)
class WitcherboardApplication

fun main(args: Array<String>) {
    runApplication<WitcherboardApplication>(*args)
}
