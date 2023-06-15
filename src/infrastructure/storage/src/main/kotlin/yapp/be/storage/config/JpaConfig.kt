package yapp.be.storage.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["yapp.be.storage.jpa"])
@EnableJpaRepositories(basePackages = ["yapp.be.storage.jpa"])
class JpaConfig
