package ru.nikzarch.witcherboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WitcherboardApplication

fun main(args: Array<String>) {
	runApplication<WitcherboardApplication>(*args)
}
