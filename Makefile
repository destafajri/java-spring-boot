# run spring-boot using default environment
run:
	@mvn spring-boot:run

# run spring-boot using dev environment
run-dev:
	@mvn spring-boot:run -Dspring-boot.run.profiles=dev

# run spring-boot using prod environment
run-prod:
	@mvn spring-boot:run -Dspring-boot.run.profiles=prod