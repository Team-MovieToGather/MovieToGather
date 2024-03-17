//package org.spartaa3.movietogather.infra
//
//import io.github.cdimascio.dotenv.Dotenv
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class DotenvConfig {
//
//    @Bean
//    fun dotenv(): Dotenv {
//        return Dotenv.configure()
//            .directory("/src/main/resources/.env")
//            .ignoreIfMalformed()
//            .ignoreIfMissing()
//            .load();
//    }
//}