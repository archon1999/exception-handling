# infrastructure

Helper assets for local development.

## Nexus via Docker Compose

The included `docker-compose.yml` starts a Sonatype Nexus 3 instance that matches the distribution management URLs in the module POMs.

### Run Nexus
```bash
cd infrastructure
docker compose up -d
```
Nexus listens on `http://localhost:8081/` with persistent data stored in the `nexus-data` volume.

### Stop and clean up
```bash
docker compose down
# Remove volumes if you want a fresh instance
# docker volume rm infrastructure_nexus-data
```

Use this registry to publish snapshots/releases from `exception-core` and `exception-i18n-spring-boot-starter` during development.
