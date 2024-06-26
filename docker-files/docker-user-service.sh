docker run -d --network ecommerce-network \
	--name user-service \
	-e "spring.cloud.config.uri=http://config-service:8888" \
	-e "spring.rabbitmq.host=rabbitmq" \
	-e "management.zipkin.tracing.endpoint=http://zipkin:9411/api/v2/spans" \
	-e "eureka.client.serviceUrl.defaultZone=http://discovery-service:8761/eureka" \
	-e "logging.file=/api/logs/users-ws.log" \
	yungyeongjun/user-service:0.0.1