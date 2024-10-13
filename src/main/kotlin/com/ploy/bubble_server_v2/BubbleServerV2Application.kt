package com.ploy.bubble_server_v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class BubbleServerV2Application  // 'open' 키워드 추가

fun main(args: Array<String>) {
	runApplication<BubbleServerV2Application>(*args)
}