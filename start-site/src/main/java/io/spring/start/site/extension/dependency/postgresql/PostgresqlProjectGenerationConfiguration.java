/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.start.site.extension.dependency.postgresql;

import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.start.site.container.ComposeFileCustomizer;
import io.spring.start.site.container.DockerImages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for generation of projects that depend on PostgreSQL.
 *
 * @author Moritz Halbritter
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnRequestedDependency("postgresql")
class PostgresqlProjectGenerationConfiguration {

	@Bean
	@ConditionalOnRequestedDependency("docker-compose")
	ComposeFileCustomizer postgresqlComposeFileCustomizer() {
		return (composeFile) -> composeFile.services()
			.add("postgres",
					DockerImages.postgres()
						.andThen((service) -> service.environment("POSTGRES_USER", "myuser")
							.environment("POSTGRES_DB", "mydatabase")
							.environment("POSTGRES_PASSWORD", "secret")
							.ports(5432)));
	}

}
