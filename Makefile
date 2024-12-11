.PHONY: infra/up infra/down infra/stop

# Start containers from Docker Compose
infra/up:
	docker-compose -f docker-compose.yml up -d

# Stop containers from Docker Compose
infra/down:
	docker-compose -f docker-compose.yml down

# Start and remove containers from Docker Compose
infra/stop:
	docker-compose -f docker-compose.yml down --volumes
